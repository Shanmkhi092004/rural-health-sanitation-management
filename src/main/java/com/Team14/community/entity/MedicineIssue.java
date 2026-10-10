
package com.Team14.community.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "medicine_issues")
public class MedicineIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Medicine ID is required")
    @Column(nullable = false)
    private Long medicineId;

    @NotNull(message = "Resident ID is required")
    @Column(nullable = false)
    private Long residentId;

    private Long consultationId;

    @Min(value = 1, message = "Issued quantity must be at least 1")
    @Column(nullable = false)
    private int quantityIssued;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    private String notes;

    public MedicineIssue() {
    }

    public MedicineIssue(Long medicineId, Long residentId,
                         Long consultationId, int quantityIssued,
                         String notes) {
        this.medicineId = medicineId;
        this.residentId = residentId;
        this.consultationId = consultationId;
        this.quantityIssued = quantityIssued;
        this.notes = notes;
        this.issuedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (issuedAt == null) {
            issuedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public int getQuantityIssued() {
        return quantityIssued;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public void setQuantityIssued(int quantityIssued) {
        this.quantityIssued = quantityIssued;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
