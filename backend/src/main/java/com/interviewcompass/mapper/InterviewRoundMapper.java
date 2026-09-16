package com.interviewcompass.mapper;

import com.interviewcompass.dto.InterviewRoundRequest;
import com.interviewcompass.dto.InterviewRoundResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewRound;
import com.interviewcompass.entity.InterviewRoundType;
import java.util.Locale;

public final class InterviewRoundMapper {

    private InterviewRoundMapper() {
    }

    public static InterviewRound toEntity(InterviewRoundRequest request, InterviewExperience interviewExperience) {
        InterviewRound interviewRound = new InterviewRound();
        interviewRound.setInterviewExperience(interviewExperience);
        interviewRound.setRoundNumber(request.getRoundNumber());
        interviewRound.setRoundType(parseRoundType(request.getRoundType()));
        interviewRound.setRoundTitle(request.getRoundTitle());
        interviewRound.setRoundDate(request.getRoundDate());
        interviewRound.setOutcome(request.getOutcome());
        interviewRound.setNotes(request.getNotes());
        return interviewRound;
    }

    public static void updateEntity(InterviewRound interviewRound, InterviewRoundRequest request) {
        interviewRound.setRoundNumber(request.getRoundNumber());
        interviewRound.setRoundType(parseRoundType(request.getRoundType()));
        interviewRound.setRoundTitle(request.getRoundTitle());
        interviewRound.setRoundDate(request.getRoundDate());
        interviewRound.setOutcome(request.getOutcome());
        interviewRound.setNotes(request.getNotes());
    }

    public static InterviewRoundResponse toResponse(InterviewRound interviewRound) {
        InterviewRoundResponse response = new InterviewRoundResponse();
        response.setId(interviewRound.getId());
        response.setInterviewExperienceId(interviewRound.getInterviewExperience().getId());
        response.setRoundNumber(interviewRound.getRoundNumber());
        response.setRoundType(interviewRound.getRoundType().name());
        response.setRoundTitle(interviewRound.getRoundTitle());
        response.setRoundDate(interviewRound.getRoundDate());
        response.setOutcome(interviewRound.getOutcome());
        response.setNotes(interviewRound.getNotes());
        response.setCreatedAt(interviewRound.getCreatedAt());
        response.setUpdatedAt(interviewRound.getUpdatedAt());
        return response;
    }

    private static InterviewRoundType parseRoundType(String roundType) {
        if (roundType == null || roundType.isBlank()) {
            throw new IllegalArgumentException("roundType is required");
        }
        return InterviewRoundType.valueOf(roundType.trim().toUpperCase(Locale.ROOT));
    }
}
