
package com.Team14.community.member3.healthcamp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampBeneficiaryRepository
        extends JpaRepository<CampBeneficiary, Long> {

    List<CampBeneficiary> findByCampId(Long campId);
}

