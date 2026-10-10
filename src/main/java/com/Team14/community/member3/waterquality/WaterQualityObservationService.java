
package com.Team14.community.member3.waterquality;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaterQualityObservationService {

    private final WaterQualityObservationRepository repository;

    public WaterQualityObservationService(
            WaterQualityObservationRepository repository) {
        this.repository = repository;
    }

    public List<WaterQualityObservation> getAllObservations() {
        return repository.findAll();
    }

    public WaterQualityObservation getObservationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Observation not found: " + id));
    }

    public WaterQualityObservation createObservation(
            WaterQualityObservation observation) {

        if (observation.getSourceName() == null ||
                observation.getSourceName().isBlank()) {
            throw new IllegalArgumentException(
                    "Water source name is required");
        }

        if (observation.getVillage() == null ||
                observation.getVillage().isBlank()) {
            throw new IllegalArgumentException(
                    "Village is required");
        }

        if (observation.getMeasuredValue() != null &&
                observation.getMeasuredValue() < 0) {
            throw new IllegalArgumentException(
                    "Measured value cannot be negative");
        }

        if (observation.getResultStatus() == null ||
                observation.getResultStatus().isBlank()) {
            observation.setResultStatus("PENDING");
        }

        observation.setResultStatus(
                observation.getResultStatus().trim().toUpperCase());

        if (!List.of("PENDING", "PASS", "FAIL")
                .contains(observation.getResultStatus())) {
            throw new IllegalArgumentException(
                    "Result status must be PENDING, PASS, or FAIL");
        }

        return repository.save(observation);
    }

    public List<WaterQualityObservation> getByVillage(
            String village) {
        return repository.findByVillageIgnoreCase(village);
    }

    public List<WaterQualityObservation> getBySource(
            String sourceName) {
        return repository.findBySourceNameIgnoreCase(sourceName);
    }

    public List<WaterQualityObservation> getByResultStatus(
            String resultStatus) {
        return repository.findByResultStatusIgnoreCase(resultStatus);
    }
}

