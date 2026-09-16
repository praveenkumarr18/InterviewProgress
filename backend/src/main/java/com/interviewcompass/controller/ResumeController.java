package com.interviewcompass.controller;

import com.interviewcompass.dto.ResumeResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.ResumeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestParam Long companyApplicationId,
            @RequestParam(required = false) String versionLabel,
            @RequestParam(required = false, defaultValue = "false") Boolean active,
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeService.uploadResume(companyApplicationId, currentUser.getUserId(), versionLabel, active, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponse> getResume(
            @PathVariable Long id,
            @RequestParam Long companyApplicationId,
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(resumeService.getResumeById(id, currentUser.getUserId(), companyApplicationId));
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getResumes(
            @RequestParam Long companyApplicationId,
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(resumeService.getResumes(companyApplicationId, currentUser.getUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Long id,
            @RequestParam Long companyApplicationId,
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        resumeService.deleteResume(id, currentUser.getUserId(), companyApplicationId);
        return ResponseEntity.noContent().build();
    }
}
