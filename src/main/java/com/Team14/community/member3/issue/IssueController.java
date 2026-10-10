package com.Team14.community.member3.issue;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@Validated
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    public record AssignmentRequest(
            @NotBlank String assignedTeam,
            String changedBy) {}

    public record StatusRequest(
            @NotBlank String status,
            String changedBy,
            String note) {}

    @GetMapping
    public List<CommunityIssue> getAll(
            @RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return issueService.getIssuesByStatus(status);
        }
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public CommunityIssue getOne(@PathVariable Long id) {
        return issueService.getIssue(id);
    }

    @PostMapping
    public ResponseEntity<CommunityIssue> create(
            @Valid @RequestBody CommunityIssue issue) {
        CommunityIssue saved = issueService.createIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}/assign")
    public CommunityIssue assign(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentRequest request) {
        return issueService.assignIssue(
                id, request.assignedTeam(), request.changedBy());
    }

    @PatchMapping("/{id}/status")
    public CommunityIssue updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request) {
        return issueService.updateStatus(
                id, request.status(),
                request.changedBy(), request.note());
    }

    @GetMapping("/{id}/history")
    public List<IssueHistory> history(@PathVariable Long id) {
        return issueService.getHistory(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(
            IllegalArgumentException ex) {
        HttpStatus status = ex.getMessage() != null
                && ex.getMessage().startsWith("Issue not found:")
                ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> conflict(
            IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }
}


