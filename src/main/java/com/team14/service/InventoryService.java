
package com.team14.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team14.dto.MedicineIssueRequest;
import com.team14.dto.ReplenishmentRequestDto;
import com.team14.entity.Medicine;
import com.team14.entity.MedicineIssue;
import com.team14.entity.ReplenishmentRequest;
import com.team14.entity.ReplenishmentRequest.ReplenishmentStatus;
import com.team14.repository.MedicineIssueRepository;
import com.team14.repository.MedicineRepository;
import com.team14.repository.ReplenishmentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final MedicineRepository medicineRepository;
    private final MedicineIssueRepository medicineIssueRepository;
    private final ReplenishmentRepository replenishmentRepository;

    public InventoryService(
            MedicineRepository medicineRepository,
            MedicineIssueRepository medicineIssueRepository,
            ReplenishmentRepository replenishmentRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineIssueRepository = medicineIssueRepository;
        this.replenishmentRepository = replenishmentRepository;
    }

    public Medicine addMedicine(Medicine medicine) {
        if (medicine.getStockQuantity() < 0
                || medicine.getReorderLevel() < 0) {
            throw new IllegalArgumentException(
                    "Stock and reorder level cannot be negative");
        }

        if (medicine.getExpiryDate() == null) {
            throw new IllegalArgumentException(
                    "Expiry date is required");
        }

        return medicineRepository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Medicine not found with ID: " + id));
    }

    public List<Medicine> searchMedicines(String name) {
        return medicineRepository
                .findByNameContainingIgnoreCase(name);
    }

    public List<Medicine> getLowStockMedicines() {
        return medicineRepository.findAll()
                .stream()
                .filter(medicine ->
                        medicine.getStockQuantity()
                                <= medicine.getReorderLevel())
                .toList();
    }

    @Transactional
    public MedicineIssue issueMedicine(
            MedicineIssueRequest request) {

        Medicine medicine = getMedicineById(
                request.getMedicineId());

        if (request.getResidentId() == null) {
            throw new IllegalArgumentException(
                    "Resident ID is required");
        }

        if (request.getQuantityIssued() <= 0) {
            throw new IllegalArgumentException(
                    "Issued quantity must be greater than zero");
        }

        if (medicine.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException(
                    "Expired medicine cannot be issued");
        }

        if (medicine.getStockQuantity()
                < request.getQuantityIssued()) {
            throw new IllegalStateException(
                    "Insufficient medicine stock");
        }

        medicine.setStockQuantity(
                medicine.getStockQuantity()
                        - request.getQuantityIssued());

        medicineRepository.save(medicine);

        MedicineIssue issue = new MedicineIssue(
                medicine.getId(),
                request.getResidentId(),
                request.getConsultationId(),
                request.getQuantityIssued(),
                request.getNotes());

        return medicineIssueRepository.save(issue);
    }

    public List<MedicineIssue> getAllMedicineIssues() {
        return medicineIssueRepository
                .findAllByOrderByIssuedAtDesc();
    }

    public List<MedicineIssue> getIssuesByResident(
            Long residentId) {
        return medicineIssueRepository
                .findByResidentIdOrderByIssuedAtDesc(
                        residentId);
    }

    public List<MedicineIssue> getIssuesByMedicine(
            Long medicineId) {
        return medicineIssueRepository
                .findByMedicineId(medicineId);
    }

    @Transactional
    public ReplenishmentRequest createReplenishmentRequest(
            ReplenishmentRequestDto request) {

        getMedicineById(request.getMedicineId());

        if (request.getRequestedQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Requested quantity must be greater than zero");
        }

        ReplenishmentRequest replenishment =
                new ReplenishmentRequest(
                        request.getMedicineId(),
                        request.getRequestedQuantity(),
                        request.getRemarks());

        return replenishmentRepository.save(replenishment);
    }

    public List<ReplenishmentRequest> getAllReplenishments() {
        return replenishmentRepository
                .findAllByOrderByRequestedAtDesc();
    }

    public List<ReplenishmentRequest> getReplenishmentsByStatus(
            ReplenishmentStatus status) {
        return replenishmentRepository.findByStatus(status);
    }

    @Transactional
    public ReplenishmentRequest updateReplenishmentStatus(
            Long requestId,
            ReplenishmentStatus newStatus) {

        ReplenishmentRequest request =
                replenishmentRepository.findById(requestId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Replenishment request not found"));

        ReplenishmentStatus currentStatus =
                request.getStatus();

        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Replenishment status is required");
        }

        if (currentStatus == ReplenishmentStatus.PENDING
                && (newStatus == ReplenishmentStatus.APPROVED
                || newStatus == ReplenishmentStatus.REJECTED)) {

            request.setStatus(newStatus);

        } else if (currentStatus == ReplenishmentStatus.APPROVED
                && newStatus == ReplenishmentStatus.RECEIVED) {

            Medicine medicine =
                    getMedicineById(request.getMedicineId());

            medicine.setStockQuantity(
                    Math.addExact(
                            medicine.getStockQuantity(),
                            request.getRequestedQuantity()));

            medicineRepository.save(medicine);
            request.setStatus(ReplenishmentStatus.RECEIVED);

        } else {
            throw new IllegalStateException(
                    "Invalid replenishment status transition: "
                            + currentStatus + " to " + newStatus);
        }

        return replenishmentRepository.save(request);
    }
}
