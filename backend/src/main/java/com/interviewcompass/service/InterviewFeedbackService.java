package com.interviewcompass.service;

import com.interviewcompass.dto.InterviewFeedbackRequest;
import com.interviewcompass.dto.InterviewFeedbackResponse;

public interface InterviewFeedbackService {

    InterviewFeedbackResponse createInterviewFeedback(Long userId, InterviewFeedbackRequest request);

    InterviewFeedbackResponse updateInterviewFeedback(Long interviewExperienceId, Long userId, InterviewFeedbackRequest request);

    InterviewFeedbackResponse getInterviewFeedbackByExperienceId(Long interviewExperienceId, Long userId);

    void deleteInterviewFeedback(Long interviewExperienceId, Long userId);
}
