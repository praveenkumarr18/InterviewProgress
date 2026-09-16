package com.interviewcompass.service;

import com.interviewcompass.dto.InterviewQuestionRequest;
import com.interviewcompass.dto.InterviewQuestionResponse;
import java.util.List;

public interface InterviewQuestionService {

    InterviewQuestionResponse createInterviewQuestion(Long userId, InterviewQuestionRequest request);

    InterviewQuestionResponse updateInterviewQuestion(Long id, Long userId, InterviewQuestionRequest request);

    InterviewQuestionResponse getInterviewQuestionById(Long id, Long userId, Long interviewRoundId);

    List<InterviewQuestionResponse> getInterviewQuestions(Long interviewRoundId, Long userId);

    List<InterviewQuestionResponse> searchQuestions(Long userId, String topic, String difficultyLevel, String search, String companyName);

    void deleteInterviewQuestion(Long id, Long userId, Long interviewRoundId);
}
