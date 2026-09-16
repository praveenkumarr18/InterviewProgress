package com.interviewcompass.mapper;

import com.interviewcompass.dto.ReminderRequest;
import com.interviewcompass.dto.ReminderResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.Reminder;
import com.interviewcompass.entity.ReminderStatus;
import java.util.Locale;

public final class ReminderMapper {

    private ReminderMapper() {
    }

    public static Reminder toEntity(ReminderRequest request, InterviewExperience interviewExperience) {
        Reminder reminder = new Reminder();
        reminder.setInterviewExperience(interviewExperience);
        reminder.setReminderTitle(request.getReminderTitle());
        reminder.setReminderMessage(request.getReminderMessage());
        reminder.setReminderAt(request.getReminderAt());
        reminder.setStatus(parseStatus(request.getStatus()));
        return reminder;
    }

    public static void updateEntity(Reminder reminder, ReminderRequest request) {
        reminder.setReminderTitle(request.getReminderTitle());
        reminder.setReminderMessage(request.getReminderMessage());
        reminder.setReminderAt(request.getReminderAt());
        reminder.setStatus(parseStatus(request.getStatus()));
    }

    public static ReminderResponse toResponse(Reminder reminder) {
        ReminderResponse response = new ReminderResponse();
        response.setId(reminder.getId());
        response.setInterviewExperienceId(reminder.getInterviewExperience().getId());
        response.setReminderTitle(reminder.getReminderTitle());
        response.setReminderMessage(reminder.getReminderMessage());
        response.setReminderAt(reminder.getReminderAt());
        response.setStatus(reminder.getStatus().name());
        response.setCreatedAt(reminder.getCreatedAt());
        response.setUpdatedAt(reminder.getUpdatedAt());
        return response;
    }

    private static ReminderStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return ReminderStatus.PENDING;
        }
        return ReminderStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
    }
}
