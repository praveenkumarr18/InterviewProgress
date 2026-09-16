package com.interviewcompass.service;

import com.interviewcompass.dto.ReminderRequest;
import com.interviewcompass.dto.ReminderResponse;
import java.util.List;

public interface ReminderService {

    ReminderResponse createReminder(Long userId, ReminderRequest request);

    ReminderResponse updateReminder(Long id, Long userId, ReminderRequest request);

    ReminderResponse getReminderById(Long id, Long userId, Long interviewExperienceId);

    List<ReminderResponse> getReminders(Long interviewExperienceId, Long userId);

    List<ReminderResponse> getRemindersForUser(Long userId);

    void deleteReminder(Long id, Long userId, Long interviewExperienceId);
}
