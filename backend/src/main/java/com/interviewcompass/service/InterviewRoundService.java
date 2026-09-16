package com.interviewcompass.service;

import com.interviewcompass.dto.InterviewRoundRequest;
import com.interviewcompass.dto.InterviewRoundResponse;
import java.util.List;

public interface InterviewRoundService {

    InterviewRoundResponse createInterviewRound(Long userId, InterviewRoundRequest request);

    InterviewRoundResponse updateInterviewRound(Long id, Long userId, InterviewRoundRequest request);

    InterviewRoundResponse getInterviewRoundById(Long id, Long userId, Long interviewExperienceId);

    List<InterviewRoundResponse> getInterviewRounds(Long interviewExperienceId, Long userId);

    void deleteInterviewRound(Long id, Long userId, Long interviewExperienceId);
}
