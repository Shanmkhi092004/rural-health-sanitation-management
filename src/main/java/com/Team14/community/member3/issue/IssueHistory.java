package com.Team14.community.member3.issue;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issue_history")
public class IssueHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long issueId;

    private String previousStatus;
    private String newStatus;
    private String assignedTeam;
    private String changedBy;
    private String note;
    private LocalDateTime changedAt;

    public IssueHistory() {
    }

    @PrePersist
    public void onCreate() {
        changedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getIssueId() { return issueId; }
    public String getPreviousStatus() { return previousStatus; }
    public String getNewStatus() { return newStatus; }
    public String getAssignedTeam() { return assignedTeam; }
    public String getChangedBy() { return changedBy; }
    public String getNote() { return note; }
    public LocalDateTime getChangedAt() { return changedAt; }

    public void setIssueId(Long issueId) { this.issueId = issueId; }
    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    public void setNote(String note) { this.note = note; }
}


