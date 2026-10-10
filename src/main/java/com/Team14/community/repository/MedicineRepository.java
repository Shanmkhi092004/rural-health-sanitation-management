
package com.Team14.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Team14.community.entity.Medicine;

import java.util.List;

@Repository
public interface MedicineRepository
        extends JpaRepository<Medicine, Long> {

    // Search medicines by name
    List<Medicine> findByNameContainingIgnoreCase(String name);

    // Find medicines whose stock is at or below the reorder level
    List<Medicine> findByStockQuantityLessThanEqual(
            int reorderLevel
    );
}
