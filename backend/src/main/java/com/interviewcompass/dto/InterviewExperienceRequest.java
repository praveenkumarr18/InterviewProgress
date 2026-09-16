package com.interviewcompass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class InterviewExperienceRequest {

    @NotNull
    @Positive
    private Long companyApplicationId;

    @NotNull
    private LocalDate interviewDate;

    @NotBlank
    private String interviewMode;

    private String experienceSummary;
    private String outcome;
    private String selfReflection;

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
}
