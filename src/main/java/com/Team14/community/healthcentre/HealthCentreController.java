
package com.Team14.community.healthcentre;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/health-centres")
public class HealthCentreController {

    private final HealthCentreRepository repository;

    public HealthCentreController(HealthCentreRepository repository) {
        this.repository = repository;
    }

    // Register a health centre
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HealthCentre addHealthCentre(
            @Valid @RequestBody HealthCentre centre) {
        centre.setId(null);
        return repository.save(centre);
    }

    // Get all health centres
    @GetMapping
    public List<HealthCentre> getAllHealthCentres() {
        return repository.findAll();
    }

    // Get one health centre by ID
    @GetMapping("/{id}")
    public HealthCentre getHealthCentreById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Health centre not found: " + id));
    }

    // Update a health centre
    @PutMapping("/{id}")
    public HealthCentre updateHealthCentre(
            @PathVariable Long id,
            @Valid @RequestBody HealthCentre updatedCentre) {

        HealthCentre existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Health centre not found: " + id));

        existing.setName(updatedCentre.getName());
        existing.setVillage(updatedCentre.getVillage());
        existing.setAddress(updatedCentre.getAddress());
        existing.setPhone(updatedCentre.getPhone());
        existing.setServices(updatedCentre.getServices());

        return repository.save(existing);
    }

    // Delete a health centre
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHealthCentre(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Health centre not found: " + id);
        }

        repository.deleteById(id);
    }
}
