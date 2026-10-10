
package com.Team14.community.member3.healthcamp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthCampService {

    private final HealthCampRepository campRepository;
    private final CampBeneficiaryRepository beneficiaryRepository;

    public HealthCampService(
            HealthCampRepository campRepository,
            CampBeneficiaryRepository beneficiaryRepository) {
        this.campRepository = campRepository;
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public List<HealthCamp> getAllCamps() {
        return campRepository.findAll();
    }

    public HealthCamp getCampById(Long id) {
        return campRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Health camp not found: " + id));
    }

    public HealthCamp createCamp(HealthCamp camp) {
        if (camp.getCampName() == null ||
                camp.getCampName().isBlank()) {
            throw new IllegalArgumentException(
                    "Camp name is required");
        }

        if (camp.getVillage() == null ||
                camp.getVillage().isBlank()) {
            throw new IllegalArgumentException(
                    "Village is required");
        }

        if (camp.getStatus() == null ||
                camp.getStatus().isBlank()) {
            camp.setStatus("PLANNED");
        }

        camp.setStatus(camp.getStatus().trim().toUpperCase());

        if (!List.of("PLANNED", "ONGOING", "COMPLETED", "CANCELLED")
                .contains(camp.getStatus())) {
            throw new IllegalArgumentException(
                    "Invalid camp status");
        }

        return campRepository.save(camp);
    }

    public List<CampBeneficiary> getBeneficiaries(Long campId) {
        getCampById(campId);
        return beneficiaryRepository.findByCampId(campId);
    }

    @Transactional
    public CampBeneficiary registerBeneficiary(
            Long campId, CampBeneficiary beneficiary) {

        getCampById(campId);

        if (beneficiary.getBeneficiaryName() == null ||
                beneficiary.getBeneficiaryName().isBlank()) {
            throw new IllegalArgumentException(
                    "Beneficiary name is required");
        }

        if (beneficiary.getAge() != null &&
                (beneficiary.getAge() < 0 ||
                 beneficiary.getAge() > 120)) {
            throw new IllegalArgumentException(
                    "Age must be between 0 and 120");
        }

        beneficiary.setCampId(campId);

        return beneficiaryRepository.save(beneficiary);
    }
}

