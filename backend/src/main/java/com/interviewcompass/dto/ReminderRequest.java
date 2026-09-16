package com.interviewcompass.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class ReminderRequest {

    @NotNull
    @Positive
    private Long interviewExperienceId;

    private String reminderTitle;
    private String reminderMessage;

    @NotNull
    private LocalDateTime reminderAt;

    private String status;

    public Long getInterviewExperienceId() {
        return interviewExperienceId;
    }

    public void setInterviewExperienceId(Long interviewExperienceId) {
        this.interviewExperienceId = interviewExperienceId;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    public LocalDateTime getReminderAt() {
        return reminderAt;
    }

    public void setReminderAt(LocalDateTime reminderAt) {
        this.reminderAt = reminderAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
