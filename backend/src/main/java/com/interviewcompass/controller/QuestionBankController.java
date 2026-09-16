package com.interviewcompass.controller;

import com.interviewcompass.dto.InterviewQuestionResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.InterviewQuestionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionBankController {

    private final InterviewQuestionService interviewQuestionService;

    public QuestionBankController(InterviewQuestionService interviewQuestionService) {
        this.interviewQuestionService = interviewQuestionService;
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestionResponse>> searchQuestions(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String difficultyLevel,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String companyName) {
        return ResponseEntity.ok(interviewQuestionService.searchQuestions(
                currentUser.getUserId(), topic, difficultyLevel, search, companyName));
    }
}
