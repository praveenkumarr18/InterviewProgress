package com.interviewcompass.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interview_experiences")
public class InterviewExperience extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_application_id", nullable = false)
    private CompanyApplication companyApplication;

    @Column(nullable = false)
    private LocalDate interviewDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InterviewMode interviewMode;

    @Column(length = 1000)
    private String experienceSummary;

    @Column(length = 200)
    private String outcome;

    @Column(length = 1000)
    private String selfReflection;

    @OneToMany(mappedBy = "interviewExperience")
    private List<InterviewRound> interviewRounds = new ArrayList<>();

    @OneToMany(mappedBy = "interviewExperience")
    private List<Reminder> reminders = new ArrayList<>();

    @OneToOne(mappedBy = "interviewExperience")
    private InterviewFeedback interviewFeedback;

    public InterviewExperience() {
    }

    public CompanyApplication getCompanyApplication() {
        return companyApplication;
    }

    public void setCompanyApplication(CompanyApplication companyApplication) {
        this.companyApplication = companyApplication;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public InterviewMode getInterviewMode() {
        return interviewMode;
    }

    public void setInterviewMode(InterviewMode interviewMode) {
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

    public List<InterviewRound> getInterviewRounds() {
        return interviewRounds;
    }

    public void setInterviewRounds(List<InterviewRound> interviewRounds) {
        this.interviewRounds = interviewRounds;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public InterviewFeedback getInterviewFeedback() {
        return interviewFeedback;
    }

    public void setInterviewFeedback(InterviewFeedback interviewFeedback) {
        this.interviewFeedback = interviewFeedback;
    }
}
