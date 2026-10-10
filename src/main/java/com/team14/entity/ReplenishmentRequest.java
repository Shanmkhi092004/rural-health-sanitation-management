
package com.team14.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "replenishment_requests")
public class ReplenishmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Medicine ID is required")
    @Column(nullable = false)
    private Long medicineId;

    @Min(value = 1, message = "Requested quantity must be at least 1")
    @Column(nullable = false)
    private int requestedQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReplenishmentStatus status = ReplenishmentStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime updatedAt;

    private String remarks;

    public ReplenishmentRequest() {
    }

    public ReplenishmentRequest(Long medicineId,
                                int requestedQuantity,
                                String remarks) {
        this.medicineId = medicineId;
        this.requestedQuantity = requestedQuantity;
        this.remarks = remarks;
        this.status = ReplenishmentStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (requestedAt == null) {
            requestedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public ReplenishmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public void setStatus(ReplenishmentStatus status) {
        this.status = status;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public enum ReplenishmentStatus {
        PENDING,
        APPROVED,
        REJECTED,
        RECEIVED
    }
}

