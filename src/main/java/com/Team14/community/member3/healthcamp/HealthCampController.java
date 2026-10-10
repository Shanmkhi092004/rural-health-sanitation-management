
package com.Team14.community.member3.healthcamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-camps")
public class HealthCampController {

    private final HealthCampService service;

    public HealthCampController(HealthCampService service) {
        this.service = service;
    }

    // Get all health camps
    @GetMapping
    public List<HealthCamp> getAllCamps() {
        return service.getAllCamps();
    }

    // Get one health camp
    @GetMapping("/{id}")
    public HealthCamp getCampById(@PathVariable Long id) {
        return service.getCampById(id);
    }

    // Create a health camp
    @PostMapping
    public ResponseEntity<?> createCamp(
            @RequestBody HealthCamp camp) {
        try {
            HealthCamp saved = service.createCamp(camp);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("error", e.getMessage()));
        }
    }

    // Register a beneficiary for a camp
    @PostMapping("/{campId}/beneficiaries")
    public ResponseEntity<?> registerBeneficiary(
            @PathVariable Long campId,
            @RequestBody CampBeneficiary beneficiary) {
        try {
            CampBeneficiary saved =
                    service.registerBeneficiary(campId, beneficiary);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("error", e.getMessage()));
        }
    }

    // Get beneficiaries for a camp
    @GetMapping("/{campId}/beneficiaries")
    public List<CampBeneficiary> getBeneficiaries(
            @PathVariable Long campId) {
        return service.getBeneficiaries(campId);
    }
}

