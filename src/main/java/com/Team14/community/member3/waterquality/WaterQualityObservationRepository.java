
package com.Team14.community.member3.waterquality;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WaterQualityObservationRepository
        extends JpaRepository<WaterQualityObservation, Long> {

    List<WaterQualityObservation> findByVillageIgnoreCase(
            String village);

    List<WaterQualityObservation> findBySourceNameIgnoreCase(
            String sourceName);

    List<WaterQualityObservation> findByResultStatusIgnoreCase(
            String resultStatus);
}

