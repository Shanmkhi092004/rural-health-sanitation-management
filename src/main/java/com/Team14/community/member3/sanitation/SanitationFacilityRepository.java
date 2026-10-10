
package com.Team14.community.member3.sanitation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SanitationFacilityRepository
        extends JpaRepository<SanitationFacility, Long> {

    List<SanitationFacility> findByVillageIgnoreCase(String village);

    List<SanitationFacility> findByConditionStatusIgnoreCase(
            String conditionStatus);

    List<SanitationFacility> findByCleanlinessStatusIgnoreCase(
            String cleanlinessStatus);
}

