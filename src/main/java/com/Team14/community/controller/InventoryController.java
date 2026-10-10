
package com.Team14.community.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Team14.community.dto.MedicineIssueRequest;
import com.Team14.community.dto.ReplenishmentRequestDto;
import com.Team14.community.entity.Medicine;
import com.Team14.community.entity.MedicineIssue;
import com.Team14.community.entity.ReplenishmentRequest;
import com.Team14.community.entity.ReplenishmentRequest.ReplenishmentStatus;
import com.Team14.community.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/medicines")
    public ResponseEntity<Medicine> addMedicine(
            @Valid @RequestBody Medicine medicine) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.addMedicine(medicine));
    }

    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines(
            @RequestParam(required = false) String name) {

        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(
                    inventoryService.searchMedicines(name));
        }

        return ResponseEntity.ok(
                inventoryService.getAllMedicines());
    }

    @GetMapping("/medicines/low-stock")
    public ResponseEntity<List<Medicine>> getLowStockMedicines() {
        return ResponseEntity.ok(
                inventoryService.getLowStockMedicines());
    }

    @GetMapping("/medicines/{id}")
    public ResponseEntity<Medicine> getMedicineById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                inventoryService.getMedicineById(id));
    }

    @PostMapping("/issues")
    public ResponseEntity<MedicineIssue> issueMedicine(
            @Valid @RequestBody MedicineIssueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.issueMedicine(request));
    }

    @GetMapping("/issues")
    public ResponseEntity<List<MedicineIssue>> getAllIssues() {
        return ResponseEntity.ok(
                inventoryService.getAllMedicineIssues());
    }

    @GetMapping("/issues/resident/{residentId}")
    public ResponseEntity<List<MedicineIssue>> getIssuesByResident(
            @PathVariable Long residentId) {
        return ResponseEntity.ok(
                inventoryService.getIssuesByResident(residentId));
    }

    @GetMapping("/issues/medicine/{medicineId}")
    public ResponseEntity<List<MedicineIssue>> getIssuesByMedicine(
            @PathVariable Long medicineId) {
        return ResponseEntity.ok(
                inventoryService.getIssuesByMedicine(medicineId));
    }

    @PostMapping("/replenishments")
    public ResponseEntity<ReplenishmentRequest> createReplenishment(
            @Valid @RequestBody ReplenishmentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService
                        .createReplenishmentRequest(request));
    }

    @GetMapping("/replenishments")
    public ResponseEntity<List<ReplenishmentRequest>>
            getAllReplenishments() {
        return ResponseEntity.ok(
                inventoryService.getAllReplenishments());
    }

    @GetMapping("/replenishments/status/{status}")
    public ResponseEntity<List<ReplenishmentRequest>>
            getReplenishmentsByStatus(
                    @PathVariable ReplenishmentStatus status) {
        return ResponseEntity.ok(
                inventoryService
                        .getReplenishmentsByStatus(status));
    }

    @PatchMapping("/replenishments/{id}/status")
    public ResponseEntity<ReplenishmentRequest>
            updateReplenishmentStatus(
                    @PathVariable Long id,
                    @RequestParam ReplenishmentStatus status) {
        return ResponseEntity.ok(
                inventoryService
                        .updateReplenishmentStatus(id, status));
    }
}

