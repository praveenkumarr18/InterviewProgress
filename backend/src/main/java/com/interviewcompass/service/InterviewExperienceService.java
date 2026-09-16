package com.interviewcompass.service;

import com.interviewcompass.dto.InterviewExperienceRequest;
import com.interviewcompass.dto.InterviewExperienceResponse;
import java.util.List;

public interface InterviewExperienceService {

    InterviewExperienceResponse createInterviewExperience(Long userId, InterviewExperienceRequest request);

    InterviewExperienceResponse updateInterviewExperience(Long id, Long userId, InterviewExperienceRequest request);

    InterviewExperienceResponse getInterviewExperienceById(Long id, Long userId, Long companyApplicationId);

    List<InterviewExperienceResponse> getInterviewExperiences(Long companyApplicationId, Long userId);

    void deleteInterviewExperience(Long id, Long userId, Long companyApplicationId);
}
