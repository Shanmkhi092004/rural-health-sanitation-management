
package com.Team14.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReplenishmentRequestDto {

    @NotNull(message = "Medicine ID is required")
    private Long medicineId;

    @Min(value = 1, message = "Requested quantity must be at least 1")
    private int requestedQuantity;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;

    public ReplenishmentRequestDto() {
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
