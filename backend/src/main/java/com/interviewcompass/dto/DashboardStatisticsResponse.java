package com.interviewcompass.dto;

public class DashboardStatisticsResponse {

    private Long userId;
    private long totalCompanies;
    private long savedCompanies;
    private long appliedCompanies;
    private long interviewScheduledCompanies;
    private long interviewingCompanies;
    private long offeredCompanies;
    private long rejectedCompanies;
    private long withdrawnCompanies;
    private long totalExperiences;
    private long totalRounds;
    private long totalQuestions;
    private long totalFeedbacks;
    private long totalResumes;
    private long totalReminders;
    private long upcomingReminders;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public void setTotalCompanies(long totalCompanies) {
        this.totalCompanies = totalCompanies;
    }

    public long getSavedCompanies() {
        return savedCompanies;
    }

    public void setSavedCompanies(long savedCompanies) {
        this.savedCompanies = savedCompanies;
    }

    public long getAppliedCompanies() {
        return appliedCompanies;
    }

    public void setAppliedCompanies(long appliedCompanies) {
        this.appliedCompanies = appliedCompanies;
    }

    public long getInterviewScheduledCompanies() {
        return interviewScheduledCompanies;
    }

    public void setInterviewScheduledCompanies(long interviewScheduledCompanies) {
        this.interviewScheduledCompanies = interviewScheduledCompanies;
    }

    public long getInterviewingCompanies() {
        return interviewingCompanies;
    }

    public void setInterviewingCompanies(long interviewingCompanies) {
        this.interviewingCompanies = interviewingCompanies;
    }

    public long getOfferedCompanies() {
        return offeredCompanies;
    }

    public void setOfferedCompanies(long offeredCompanies) {
        this.offeredCompanies = offeredCompanies;
    }

    public long getRejectedCompanies() {
        return rejectedCompanies;
    }

    public void setRejectedCompanies(long rejectedCompanies) {
        this.rejectedCompanies = rejectedCompanies;
    }

    public long getWithdrawnCompanies() {
        return withdrawnCompanies;
    }

    public void setWithdrawnCompanies(long withdrawnCompanies) {
        this.withdrawnCompanies = withdrawnCompanies;
    }

    public long getTotalExperiences() {
        return totalExperiences;
    }

    public void setTotalExperiences(long totalExperiences) {
        this.totalExperiences = totalExperiences;
    }

    public long getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(long totalRounds) {
        this.totalRounds = totalRounds;
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public long getTotalFeedbacks() {
        return totalFeedbacks;
    }

    public void setTotalFeedbacks(long totalFeedbacks) {
        this.totalFeedbacks = totalFeedbacks;
    }

    public long getTotalResumes() {
        return totalResumes;
    }

    public void setTotalResumes(long totalResumes) {
        this.totalResumes = totalResumes;
    }

    public long getTotalReminders() {
        return totalReminders;
    }

    public void setTotalReminders(long totalReminders) {
        this.totalReminders = totalReminders;
    }

    public long getUpcomingReminders() {
        return upcomingReminders;
    }

    public void setUpcomingReminders(long upcomingReminders) {
        this.upcomingReminders = upcomingReminders;
    }
}
