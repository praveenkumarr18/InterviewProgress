package com.interviewcompass.controller;

import com.interviewcompass.dto.InterviewQuestionRequest;
import com.interviewcompass.dto.InterviewQuestionResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.InterviewQuestionService;
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
@RequestMapping("/rounds/{interviewRoundId}/questions")
public class InterviewQuestionController {

    private final InterviewQuestionService interviewQuestionService;

    public InterviewQuestionController(InterviewQuestionService interviewQuestionService) {
        this.interviewQuestionService = interviewQuestionService;
    }

    @PostMapping
    public ResponseEntity<InterviewQuestionResponse> createInterviewQuestion(
            @PathVariable Long interviewRoundId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewQuestionRequest request) {
        request.setInterviewRoundId(interviewRoundId);
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewQuestionService.createInterviewQuestion(currentUser.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestionResponse>> getInterviewQuestions(
            @PathVariable Long interviewRoundId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewQuestionService.getInterviewQuestions(interviewRoundId, currentUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewQuestionResponse> getInterviewQuestionById(
            @PathVariable Long interviewRoundId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(interviewQuestionService.getInterviewQuestionById(id, currentUser.getUserId(), interviewRoundId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewQuestionResponse> updateInterviewQuestion(
            @PathVariable Long interviewRoundId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody InterviewQuestionRequest request) {
        request.setInterviewRoundId(interviewRoundId);
        return ResponseEntity.ok(interviewQuestionService.updateInterviewQuestion(id, currentUser.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterviewQuestion(
            @PathVariable Long interviewRoundId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        interviewQuestionService.deleteInterviewQuestion(id, currentUser.getUserId(), interviewRoundId);
        return ResponseEntity.noContent().build();
    }
}
