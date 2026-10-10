package com.Team14.community.member3.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository
        extends JpaRepository<CommunityIssue, Long> {

    List<CommunityIssue> findByStatusIgnoreCase(String status);

    List<CommunityIssue> findByVillageIgnoreCase(String village);

    List<CommunityIssue> findByCategoryIgnoreCase(String category);
}

