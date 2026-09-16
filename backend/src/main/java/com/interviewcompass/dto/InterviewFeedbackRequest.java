package com.interviewcompass.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class InterviewFeedbackRequest {

    @NotNull
    @Positive
    private Long interviewExperienceId;

    private String strengths;
    private String weaknesses;
    private String improvementNotes;
    private String interviewerFeedback;

    public Long getInterviewExperienceId() {
        return interviewExperienceId;
    }

    public void setInterviewExperienceId(Long interviewExperienceId) {
        this.interviewExperienceId = interviewExperienceId;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getImprovementNotes() {
        return improvementNotes;
    }

    public void setImprovementNotes(String improvementNotes) {
        this.improvementNotes = improvementNotes;
    }

    public String getInterviewerFeedback() {
        return interviewerFeedback;
    }

    public void setInterviewerFeedback(String interviewerFeedback) {
        this.interviewerFeedback = interviewerFeedback;
    }
}
