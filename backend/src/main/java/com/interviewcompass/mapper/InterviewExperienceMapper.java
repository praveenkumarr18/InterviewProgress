package com.interviewcompass.mapper;

import com.interviewcompass.dto.InterviewExperienceRequest;
import com.interviewcompass.dto.InterviewExperienceResponse;
import com.interviewcompass.entity.CompanyApplication;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewMode;
import java.util.Locale;

public final class InterviewExperienceMapper {

    private InterviewExperienceMapper() {
    }

    public static InterviewExperience toEntity(InterviewExperienceRequest request, CompanyApplication companyApplication) {
        InterviewExperience interviewExperience = new InterviewExperience();
        interviewExperience.setCompanyApplication(companyApplication);
        interviewExperience.setInterviewDate(request.getInterviewDate());
        interviewExperience.setInterviewMode(parseMode(request.getInterviewMode()));
        interviewExperience.setExperienceSummary(request.getExperienceSummary());
        interviewExperience.setOutcome(request.getOutcome());
        interviewExperience.setSelfReflection(request.getSelfReflection());
        return interviewExperience;
    }

    public static void updateEntity(InterviewExperience interviewExperience, InterviewExperienceRequest request) {
        interviewExperience.setInterviewDate(request.getInterviewDate());
        interviewExperience.setInterviewMode(parseMode(request.getInterviewMode()));
        interviewExperience.setExperienceSummary(request.getExperienceSummary());
        interviewExperience.setOutcome(request.getOutcome());
        interviewExperience.setSelfReflection(request.getSelfReflection());
    }

    public static InterviewExperienceResponse toResponse(InterviewExperience interviewExperience) {
        InterviewExperienceResponse response = new InterviewExperienceResponse();
        response.setId(interviewExperience.getId());
        response.setCompanyApplicationId(interviewExperience.getCompanyApplication().getId());
        response.setInterviewDate(interviewExperience.getInterviewDate());
        response.setInterviewMode(interviewExperience.getInterviewMode().name());
        response.setExperienceSummary(interviewExperience.getExperienceSummary());
        response.setOutcome(interviewExperience.getOutcome());
        response.setSelfReflection(interviewExperience.getSelfReflection());
        response.setCreatedAt(interviewExperience.getCreatedAt());
        response.setUpdatedAt(interviewExperience.getUpdatedAt());
        return response;
    }

    private static InterviewMode parseMode(String interviewMode) {
        if (interviewMode == null || interviewMode.isBlank()) {
            throw new IllegalArgumentException("interviewMode is required");
        }
        return InterviewMode.valueOf(interviewMode.trim().toUpperCase(Locale.ROOT));
    }
}
