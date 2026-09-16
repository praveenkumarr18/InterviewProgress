package com.interviewcompass.controller;

import com.interviewcompass.dto.InterviewExperienceRequest;
import com.interviewcompass.dto.InterviewExperienceResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.InterviewExperienceService;
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
@RequestMapping("/companies/{companyApplicationId}/experiences")
public class InterviewExperienceController {

    private final InterviewExperienceService interviewExperienceService;

    public InterviewExperienceController(InterviewExperienceService interviewExperienceService) {
        this.interviewExperienceService = interviewExperienceService;
    }

    @PostMapping
    public ResponseEntity<InterviewExperienceResponse> createInterviewExperience(
            @PathVariable Long companyApplicationId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewExperienceRequest request) {
        request.setCompanyApplicationId(companyApplicationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewExperienceService.createInterviewExperience(currentUser.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<InterviewExperienceResponse>> getInterviewExperiences(
            @PathVariable Long companyApplicationId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewExperienceService.getInterviewExperiences(companyApplicationId, currentUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewExperienceResponse> getInterviewExperienceById(
            @PathVariable Long companyApplicationId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewExperienceService.getInterviewExperienceById(id, currentUser.getUserId(), companyApplicationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewExperienceResponse> updateInterviewExperience(
            @PathVariable Long companyApplicationId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewExperienceRequest request) {
        request.setCompanyApplicationId(companyApplicationId);
        return ResponseEntity.ok(interviewExperienceService.updateInterviewExperience(id, currentUser.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewExperience(
            @PathVariable Long companyApplicationId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        interviewExperienceService.deleteInterviewExperience(id, currentUser.getUserId(), companyApplicationId);
        return ResponseEntity.noContent().build();
    }
}
