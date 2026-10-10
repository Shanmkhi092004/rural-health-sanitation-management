
package com.Team14.community.member3.waterquality;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/water-quality")
public class WaterQualityObservationController {

    private final WaterQualityObservationService service;

    public WaterQualityObservationController(
            WaterQualityObservationService service) {
        this.service = service;
    }

    // Get all observations
    @GetMapping
    public List<WaterQualityObservation> getAllObservations() {
        return service.getAllObservations();
    }

    // Get one observation
    @GetMapping("/{id}")
    public WaterQualityObservation getObservationById(
            @PathVariable Long id) {
        return service.getObservationById(id);
    }

    // Create an observation
    @PostMapping
    public ResponseEntity<?> createObservation(
            @RequestBody WaterQualityObservation observation) {
        try {
            WaterQualityObservation saved =
                    service.createObservation(observation);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(saved);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Filter by village
    @GetMapping("/village/{village}")
    public List<WaterQualityObservation> getByVillage(
            @PathVariable String village) {
        return service.getByVillage(village);
    }

    // Filter by source
    @GetMapping("/source/{sourceName}")
    public List<WaterQualityObservation> getBySource(
            @PathVariable String sourceName) {
        return service.getBySource(sourceName);
    }

    // Filter by result status
    @GetMapping("/status/{resultStatus}")
    public List<WaterQualityObservation> getByResultStatus(
            @PathVariable String resultStatus) {
        return service.getByResultStatus(resultStatus);
    }
}

