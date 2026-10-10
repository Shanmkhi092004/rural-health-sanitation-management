package com.Team14.community.member3.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueHistoryRepository
        extends JpaRepository<IssueHistory, Long> {

    List<IssueHistory> findByIssueIdOrderByChangedAtDesc(
            Long issueId);
}


