package com.Team14.community.consultation;

import com.Team14.community.resident.ResidentRepository;
import com.Team14.community.healthcentre.HealthCentreRepository;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationRepository consultationRepository;
    private final ResidentRepository residentRepository;
    private final HealthCentreRepository healthCentreRepository;

    public ConsultationController(
            ConsultationRepository consultationRepository,
            ResidentRepository residentRepository,
            HealthCentreRepository healthCentreRepository) {

        this.consultationRepository = consultationRepository;
        this.residentRepository = residentRepository;
        this.healthCentreRepository = healthCentreRepository;
    }

    private void validateReferences(Long residentId, Long healthCentreId) {

        if (residentId == null ||
                !residentRepository.existsById(residentId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Resident not found: " + residentId);
        }

        if (healthCentreId == null ||
                !healthCentreRepository.existsById(healthCentreId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Health centre not found: " + healthCentreId);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consultation createConsultation(
            @Valid @RequestBody Consultation consultation) {

        validateReferences(
                consultation.getResidentId(),
                consultation.getHealthCentreId());

        consultation.setId(null);
        return consultationRepository.save(consultation);
    }

    @GetMapping
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Consultation getConsultationById(@PathVariable Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Consultation not found: " + id));
    }

    @PutMapping("/{id}")
    public Consultation updateConsultation(
            @PathVariable("id") Long id,
            @Valid @RequestBody Consultation updatedConsultation) {

        Consultation existing = consultationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Consultation not found: " + id));

        validateReferences(
                updatedConsultation.getResidentId(),
                updatedConsultation.getHealthCentreId());

        existing.setResidentId(updatedConsultation.getResidentId());
        existing.setHealthCentreId(updatedConsultation.getHealthCentreId());
        existing.setConsultationDate(
                updatedConsultation.getConsultationDate());
        existing.setSymptoms(updatedConsultation.getSymptoms());
        existing.setDiagnosis(updatedConsultation.getDiagnosis());
        existing.setTreatment(updatedConsultation.getTreatment());
        existing.setFollowUpNotes(updatedConsultation.getFollowUpNotes());

        return consultationRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsultation(@PathVariable Long id) {

        if (!consultationRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Consultation not found: " + id);
        }

        consultationRepository.deleteById(id);
    }
}