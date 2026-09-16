package com.interviewcompass.controller;

import com.interviewcompass.dto.InterviewFeedbackRequest;
import com.interviewcompass.dto.InterviewFeedbackResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.InterviewFeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experiences/{interviewExperienceId}/feedback")
public class InterviewFeedbackController {

    private final InterviewFeedbackService interviewFeedbackService;

    public InterviewFeedbackController(InterviewFeedbackService interviewFeedbackService) {
        this.interviewFeedbackService = interviewFeedbackService;
    }

    @PostMapping
    public ResponseEntity<InterviewFeedbackResponse> createInterviewFeedback(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewFeedbackRequest request) {
        request.setInterviewExperienceId(interviewExperienceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewFeedbackService.createInterviewFeedback(currentUser.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<InterviewFeedbackResponse> getInterviewFeedbackByExperienceId(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewFeedbackService.getInterviewFeedbackByExperienceId(interviewExperienceId, currentUser.getUserId()));
    }

    @PutMapping
    public ResponseEntity<InterviewFeedbackResponse> updateInterviewFeedback(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewFeedbackRequest request) {
        request.setInterviewExperienceId(interviewExperienceId);
        return ResponseEntity.ok(interviewFeedbackService.updateInterviewFeedback(interviewExperienceId, currentUser.getUserId(), request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteInterviewFeedback(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        interviewFeedbackService.deleteInterviewFeedback(interviewExperienceId, currentUser.getUserId());
        return ResponseEntity.noContent().build();
    }
}
