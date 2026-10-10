
package com.Team14.community.member3.sanitation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sanitation-facilities")
public class SanitationFacilityController {

    private final SanitationFacilityService service;

    public SanitationFacilityController(
            SanitationFacilityService service) {
        this.service = service;
    }

    // Get all facilities
    @GetMapping
    public List<SanitationFacility> getAllFacilities() {
        return service.getAllFacilities();
    }

    // Get one facility
    @GetMapping("/{id}")
    public SanitationFacility getFacilityById(
            @PathVariable Long id) {
        return service.getFacilityById(id);
    }

    // Create a facility
    @PostMapping
    public ResponseEntity<?> createFacility(
            @RequestBody SanitationFacility facility) {
        try {
            SanitationFacility saved =
                    service.createFacility(facility);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(saved);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Filter by village
    @GetMapping("/village/{village}")
    public List<SanitationFacility> getByVillage(
            @PathVariable String village) {
        return service.getFacilitiesByVillage(village);
    }

    // Filter by condition
    @GetMapping("/condition/{condition}")
    public List<SanitationFacility> getByCondition(
            @PathVariable String condition) {
        return service.getFacilitiesByCondition(condition);
    }

    // Filter by cleanliness
    @GetMapping("/cleanliness/{cleanliness}")
    public List<SanitationFacility> getByCleanliness(
            @PathVariable String cleanliness) {
        return service.getFacilitiesByCleanliness(cleanliness);
    }
}

