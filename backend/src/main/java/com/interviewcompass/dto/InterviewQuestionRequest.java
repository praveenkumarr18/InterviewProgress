package com.interviewcompass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class InterviewQuestionRequest {

    @NotNull
    @Positive
    private Long interviewRoundId;

    @NotBlank
    private String questionText;

    private String suggestedAnswer;
    private String topic;
    private String difficultyLevel;

    public Long getInterviewRoundId() {
        return interviewRoundId;
    }

    public void setInterviewRoundId(Long interviewRoundId) {
        this.interviewRoundId = interviewRoundId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getSuggestedAnswer() {
        return suggestedAnswer;
    }

    public void setSuggestedAnswer(String suggestedAnswer) {
        this.suggestedAnswer = suggestedAnswer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
