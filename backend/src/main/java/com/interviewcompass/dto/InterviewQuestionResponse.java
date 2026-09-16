package com.interviewcompass.dto;

import java.time.LocalDateTime;

public class InterviewQuestionResponse {

    private Long id;
    private Long interviewRoundId;
    private String questionText;
    private String suggestedAnswer;
    private String topic;
    private String difficultyLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String companyName;
    private Integer roundNumber;
    private String roundTitle;
    private Long interviewExperienceId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getRoundTitle() {
        return roundTitle;
    }

    public void setRoundTitle(String roundTitle) {
        this.roundTitle = roundTitle;
    }

    public Long getInterviewExperienceId() {
        return interviewExperienceId;
    }

    public void setInterviewExperienceId(Long interviewExperienceId) {
        this.interviewExperienceId = interviewExperienceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
