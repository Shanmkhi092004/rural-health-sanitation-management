package com.Team14.community.member3.issue;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_issues")
public class CommunityIssue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String category; // SANITATION or WATER

    @Column(nullable = false)
    private String priority = "MEDIUM";

    @Column(nullable = false)
    private String status = "OPEN";

    private String assignedTeam;
    private String reportedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommunityIssue() {
    }

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getVillage() { return village; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getAssignedTeam() { return assignedTeam; }
    public String getReportedBy() { return reportedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setVillage(String village) {
        this.village = village;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }
}

