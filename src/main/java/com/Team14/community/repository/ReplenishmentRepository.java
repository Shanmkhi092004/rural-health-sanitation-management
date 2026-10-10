
package com.Team14.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Team14.community.entity.ReplenishmentRequest;
import com.Team14.community.entity.ReplenishmentRequest.ReplenishmentStatus;

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

