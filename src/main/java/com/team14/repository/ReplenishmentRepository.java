
package com.Team14.repository;

import com.Team14.entity.ReplenishmentRequest;
import com.Team14.entity.ReplenishmentRequest.ReplenishmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplenishmentRepository
        extends JpaRepository<ReplenishmentRequest, Long> {

    List<ReplenishmentRequest> findByMedicineId(Long medicineId);

    List<ReplenishmentRequest> findByStatus(
            ReplenishmentStatus status
    );

    List<ReplenishmentRequest> findAllByOrderByRequestedAtDesc();
}

