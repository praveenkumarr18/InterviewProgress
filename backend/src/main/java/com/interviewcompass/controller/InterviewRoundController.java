package com.interviewcompass.controller;

import com.interviewcompass.dto.InterviewRoundRequest;
import com.interviewcompass.dto.InterviewRoundResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.InterviewRoundService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/experiences/{interviewExperienceId}/rounds")
public class InterviewRoundController {

    private final InterviewRoundService interviewRoundService;

    public InterviewRoundController(InterviewRoundService interviewRoundService) {
        this.interviewRoundService = interviewRoundService;
    }

    @PostMapping
    public ResponseEntity<InterviewRoundResponse> createInterviewRound(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewRoundRequest request) {
        request.setInterviewExperienceId(interviewExperienceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewRoundService.createInterviewRound(currentUser.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<InterviewRoundResponse>> getInterviewRounds(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewRoundService.getInterviewRounds(interviewExperienceId, currentUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewRoundResponse> getInterviewRoundById(
            @PathVariable Long interviewExperienceId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewRoundService.getInterviewRoundById(id, currentUser.getUserId(), interviewExperienceId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewRoundResponse> updateInterviewRound(
            @PathVariable Long interviewExperienceId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewRoundRequest request) {
        request.setInterviewExperienceId(interviewExperienceId);
        return ResponseEntity.ok(interviewRoundService.updateInterviewRound(id, currentUser.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewRound(
            @PathVariable Long interviewExperienceId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        interviewRoundService.deleteInterviewRound(id, currentUser.getUserId(), interviewExperienceId);
        return ResponseEntity.noContent().build();
    }
}
