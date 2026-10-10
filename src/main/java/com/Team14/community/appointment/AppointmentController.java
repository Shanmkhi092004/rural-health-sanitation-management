package com.Team14.community.appointment;

import com.Team14.community.resident.ResidentRepository;
import com.Team14.community.healthcentre.HealthCentreRepository;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final ResidentRepository residentRepository;
    private final HealthCentreRepository healthCentreRepository;

    public AppointmentController(
            AppointmentRepository appointmentRepository,
            ResidentRepository residentRepository,
            HealthCentreRepository healthCentreRepository) {

        this.appointmentRepository = appointmentRepository;
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
    public Appointment createAppointment(
            @Valid @RequestBody Appointment appointment) {

        validateReferences(
                appointment.getResidentId(),
                appointment.getHealthCentreId());

        appointment.setId(null);
        return appointmentRepository.save(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Appointment not found: " + id));
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(
            @PathVariable("id") Long id,
            @Valid @RequestBody Appointment updatedAppointment) {

        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Appointment not found: " + id));

        validateReferences(
                updatedAppointment.getResidentId(),
                updatedAppointment.getHealthCentreId());

        existing.setResidentId(updatedAppointment.getResidentId());
        existing.setHealthCentreId(updatedAppointment.getHealthCentreId());
        existing.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existing.setReason(updatedAppointment.getReason());
        existing.setStatus(updatedAppointment.getStatus());

        return appointmentRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable Long id) {

        if (!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Appointment not found: " + id);
        }

        appointmentRepository.deleteById(id);
    }
}