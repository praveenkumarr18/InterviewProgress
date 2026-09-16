package com.interviewcompass.mapper;

import com.interviewcompass.dto.InterviewQuestionRequest;
import com.interviewcompass.dto.InterviewQuestionResponse;
import com.interviewcompass.entity.InterviewQuestion;
import com.interviewcompass.entity.InterviewRound;

public final class InterviewQuestionMapper {

    private InterviewQuestionMapper() {
    }

    public static InterviewQuestion toEntity(InterviewQuestionRequest request, InterviewRound interviewRound) {
        InterviewQuestion interviewQuestion = new InterviewQuestion();
        interviewQuestion.setInterviewRound(interviewRound);
        interviewQuestion.setQuestionText(request.getQuestionText());
        interviewQuestion.setSuggestedAnswer(request.getSuggestedAnswer());
        interviewQuestion.setTopic(request.getTopic());
        interviewQuestion.setDifficultyLevel(request.getDifficultyLevel());
        return interviewQuestion;
    }

    public static void updateEntity(InterviewQuestion interviewQuestion, InterviewQuestionRequest request) {
        interviewQuestion.setQuestionText(request.getQuestionText());
        interviewQuestion.setSuggestedAnswer(request.getSuggestedAnswer());
        interviewQuestion.setTopic(request.getTopic());
        interviewQuestion.setDifficultyLevel(request.getDifficultyLevel());
    }

    public static InterviewQuestionResponse toResponse(InterviewQuestion interviewQuestion) {
        InterviewQuestionResponse response = new InterviewQuestionResponse();
        response.setId(interviewQuestion.getId());
        response.setQuestionText(interviewQuestion.getQuestionText());
        response.setSuggestedAnswer(interviewQuestion.getSuggestedAnswer());
        response.setTopic(interviewQuestion.getTopic());
        response.setDifficultyLevel(interviewQuestion.getDifficultyLevel());
        response.setCreatedAt(interviewQuestion.getCreatedAt());
        response.setUpdatedAt(interviewQuestion.getUpdatedAt());

        if (interviewQuestion.getInterviewRound() != null) {
            response.setInterviewRoundId(interviewQuestion.getInterviewRound().getId());
            response.setRoundNumber(interviewQuestion.getInterviewRound().getRoundNumber());
            response.setRoundTitle(interviewQuestion.getInterviewRound().getRoundTitle());
            if (interviewQuestion.getInterviewRound().getInterviewExperience() != null) {
                response.setInterviewExperienceId(interviewQuestion.getInterviewRound().getInterviewExperience().getId());
                if (interviewQuestion.getInterviewRound().getInterviewExperience().getCompanyApplication() != null) {
                    response.setCompanyName(interviewQuestion.getInterviewRound().getInterviewExperience().getCompanyApplication().getCompanyName());
                }
            }
        }
        return response;
    }
}
