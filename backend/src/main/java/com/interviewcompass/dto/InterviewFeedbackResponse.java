package com.interviewcompass.dto;

import java.time.LocalDateTime;

public class InterviewFeedbackResponse {

    private Long id;
    private Long interviewExperienceId;
    private String strengths;
    private String weaknesses;
    private String improvementNotes;
    private String interviewerFeedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
