package com.interviewcompass.mapper;

import com.interviewcompass.dto.InterviewFeedbackRequest;
import com.interviewcompass.dto.InterviewFeedbackResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewFeedback;

public final class InterviewFeedbackMapper {

    private InterviewFeedbackMapper() {
    }

    public static InterviewFeedback toEntity(InterviewFeedbackRequest request, InterviewExperience interviewExperience) {
        InterviewFeedback interviewFeedback = new InterviewFeedback();
        interviewFeedback.setInterviewExperience(interviewExperience);
        interviewFeedback.setStrengths(request.getStrengths());
        interviewFeedback.setWeaknesses(request.getWeaknesses());
        interviewFeedback.setImprovementNotes(request.getImprovementNotes());
        interviewFeedback.setInterviewerFeedback(request.getInterviewerFeedback());
        return interviewFeedback;
    }

    public static void updateEntity(InterviewFeedback interviewFeedback, InterviewFeedbackRequest request) {
        interviewFeedback.setStrengths(request.getStrengths());
        interviewFeedback.setWeaknesses(request.getWeaknesses());
        interviewFeedback.setImprovementNotes(request.getImprovementNotes());
        interviewFeedback.setInterviewerFeedback(request.getInterviewerFeedback());
    }

    public static InterviewFeedbackResponse toResponse(InterviewFeedback interviewFeedback) {
        InterviewFeedbackResponse response = new InterviewFeedbackResponse();
        response.setId(interviewFeedback.getId());
        response.setInterviewExperienceId(interviewFeedback.getInterviewExperience().getId());
        response.setStrengths(interviewFeedback.getStrengths());
        response.setWeaknesses(interviewFeedback.getWeaknesses());
        response.setImprovementNotes(interviewFeedback.getImprovementNotes());
        response.setInterviewerFeedback(interviewFeedback.getInterviewerFeedback());
        response.setCreatedAt(interviewFeedback.getCreatedAt());
        response.setUpdatedAt(interviewFeedback.getUpdatedAt());
        return response;
    }
}
