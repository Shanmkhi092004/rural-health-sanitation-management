
package com.Team14.community.member3.sanitation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanitationFacilityService {

    private final SanitationFacilityRepository repository;

    public SanitationFacilityService(
            SanitationFacilityRepository repository) {
        this.repository = repository;
    }

    public List<SanitationFacility> getAllFacilities() {
        return repository.findAll();
    }

    public SanitationFacility getFacilityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Facility not found: " + id));
    }

    public SanitationFacility createFacility(
            SanitationFacility facility) {

        if (facility.getFacilityName() == null ||
                facility.getFacilityName().isBlank()) {
            throw new IllegalArgumentException(
                    "Facility name is required");
        }

        if (facility.getVillage() == null ||
                facility.getVillage().isBlank()) {
            throw new IllegalArgumentException(
                    "Village is required");
        }

        if (facility.getConditionStatus() == null ||
                facility.getConditionStatus().isBlank()) {
            facility.setConditionStatus("GOOD");
        }

        if (facility.getCleanlinessStatus() == null ||
                facility.getCleanlinessStatus().isBlank()) {
            facility.setCleanlinessStatus("CLEAN");
        }

        facility.setConditionStatus(
                facility.getConditionStatus().trim().toUpperCase());

        facility.setCleanlinessStatus(
                facility.getCleanlinessStatus().trim().toUpperCase());

        if (!List.of("GOOD", "NEEDS_REPAIR", "OUT_OF_ORDER")
                .contains(facility.getConditionStatus())) {
            throw new IllegalArgumentException(
                    "Invalid condition status");
        }

        if (!List.of("CLEAN", "MODERATE", "DIRTY")
                .contains(facility.getCleanlinessStatus())) {
            throw new IllegalArgumentException(
                    "Invalid cleanliness status");
        }

        return repository.save(facility);
    }

    public List<SanitationFacility> getFacilitiesByVillage(
            String village) {
        return repository.findByVillageIgnoreCase(village);
    }

    public List<SanitationFacility> getFacilitiesByCondition(
            String condition) {
        return repository.findByConditionStatusIgnoreCase(condition);
    }

    public List<SanitationFacility> getFacilitiesByCleanliness(
            String cleanliness) {
        return repository.findByCleanlinessStatusIgnoreCase(cleanliness);
    }
}

