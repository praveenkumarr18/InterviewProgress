package com.interviewcompass.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InterviewExperienceResponse {

    private Long id;
    private Long companyApplicationId;
    private LocalDate interviewDate;
    private String interviewMode;
    private String experienceSummary;
    private String outcome;
    private String selfReflection;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyApplicationId() {
        return companyApplicationId;
    }

    public void setCompanyApplicationId(Long companyApplicationId) {
        this.companyApplicationId = companyApplicationId;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getInterviewMode() {
        return interviewMode;
    }

    public void setInterviewMode(String interviewMode) {
        this.interviewMode = interviewMode;
    }

    public String getExperienceSummary() {
        return experienceSummary;
    }

    public void setExperienceSummary(String experienceSummary) {
        this.experienceSummary = experienceSummary;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getSelfReflection() {
        return selfReflection;
    }

    public void setSelfReflection(String selfReflection) {
        this.selfReflection = selfReflection;
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
