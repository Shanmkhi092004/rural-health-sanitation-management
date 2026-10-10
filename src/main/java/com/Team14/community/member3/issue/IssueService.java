package com.Team14.community.member3.issue;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueHistoryRepository historyRepository;

    public IssueService(
            IssueRepository issueRepository,
            IssueHistoryRepository historyRepository) {
        this.issueRepository = issueRepository;
        this.historyRepository = historyRepository;
    }

    public List<CommunityIssue> getAllIssues() {
        return issueRepository.findAll();
    }

    public List<CommunityIssue> getIssuesByStatus(String status) {
        return issueRepository.findByStatusIgnoreCase(status);
    }

    public CommunityIssue getIssue(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Issue not found: " + id));
    }

    @Transactional
    public CommunityIssue createIssue(CommunityIssue issue) {
        requireText(issue.getTitle(), "Title");
        requireText(issue.getDescription(), "Description");
        requireText(issue.getVillage(), "Village");

        String category = normalize(issue.getCategory());
        if (!Set.of("SANITATION", "WATER").contains(category)) {
            throw new IllegalArgumentException(
                    "Category must be SANITATION or WATER");
        }

        String priority = issue.getPriority() == null
                ? "MEDIUM" : normalize(issue.getPriority());

        if (!Set.of("LOW", "MEDIUM", "HIGH", "URGENT")
                .contains(priority)) {
            throw new IllegalArgumentException(
                    "Priority must be LOW, MEDIUM, HIGH or URGENT");
        }

        issue.setId(null);
        issue.setCategory(category);
        issue.setPriority(priority);
        issue.setStatus("OPEN");
        issue.setAssignedTeam(null);

        CommunityIssue saved = issueRepository.save(issue);

        recordHistory(saved, null, "OPEN",
                "Complaint created", issue.getReportedBy());

        return saved;
    }

    @Transactional
    public CommunityIssue assignIssue(
            Long id, String team, String changedBy) {

        requireText(team, "Assigned team");

        CommunityIssue issue = getIssue(id);

        if ("RESOLVED".equals(issue.getStatus())) {
            throw new IllegalStateException(
                    "A resolved issue cannot be reassigned");
        }

        String previousStatus = issue.getStatus();
        issue.setAssignedTeam(team.trim());
        issue.setStatus("ASSIGNED");

        CommunityIssue saved = issueRepository.save(issue);

        recordHistory(saved, previousStatus, "ASSIGNED",
                "Assigned to " + team.trim(), changedBy);

        return saved;
    }

    @Transactional
    public CommunityIssue updateStatus(
            Long id, String requestedStatus,
            String changedBy, String note) {

        String status = normalize(requestedStatus);

        if (!Set.of("OPEN", "ASSIGNED", "IN_PROGRESS", "RESOLVED")
                .contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid status");
        }

        CommunityIssue issue = getIssue(id);
        String previous = issue.getStatus();

        if ("RESOLVED".equals(previous)) {
            throw new IllegalStateException(
                    "Resolved issues cannot be reopened");
        }

        if (previous.equals(status)) {
            throw new IllegalArgumentException(
                    "Issue already has this status");
        }

        if ("ASSIGNED".equals(status)
                && (issue.getAssignedTeam() == null
                || issue.getAssignedTeam().isBlank())) {
            throw new IllegalStateException(
                    "Assign a team first");
        }

        if ("IN_PROGRESS".equals(status)
                && !"ASSIGNED".equals(previous)
                && !"IN_PROGRESS".equals(previous)) {
            throw new IllegalStateException(
                    "Assign the issue before starting work");
        }

        if ("RESOLVED".equals(status)
                && !"IN_PROGRESS".equals(previous)) {
            throw new IllegalStateException(
                    "Issue must be in progress before resolution");
        }

        issue.setStatus(status);
        CommunityIssue saved = issueRepository.save(issue);

        recordHistory(saved, previous, status, note, changedBy);

        return saved;
    }

    public List<IssueHistory> getHistory(Long issueId) {
        getIssue(issueId);
        return historyRepository
                .findByIssueIdOrderByChangedAtDesc(issueId);
    }

    private void recordHistory(
            CommunityIssue issue,
            String previousStatus,
            String newStatus,
            String note,
            String changedBy) {

        IssueHistory history = new IssueHistory();
        history.setIssueId(issue.getId());
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setAssignedTeam(issue.getAssignedTeam());
        history.setChangedBy(
                changedBy == null || changedBy.isBlank()
                        ? "Demo User" : changedBy.trim());
        history.setNote(note);

        historyRepository.save(history);
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " is required");
        }
    }
}


