
package com.Team14.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Team14.community.entity.MedicineIssue;

import java.util.List;

@Repository
public interface MedicineIssueRepository
        extends JpaRepository<MedicineIssue, Long> {

    List<MedicineIssue> findByResidentId(Long residentId);

    List<MedicineIssue> findByMedicineId(Long medicineId);

    List<MedicineIssue> findByResidentIdOrderByIssuedAtDesc(
            Long residentId
    );

    List<MedicineIssue> findAllByOrderByIssuedAtDesc();
}
