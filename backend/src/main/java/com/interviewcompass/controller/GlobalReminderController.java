package com.interviewcompass.controller;

import com.interviewcompass.dto.ReminderResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.ReminderService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminders")
public class GlobalReminderController {

    private final ReminderService reminderService;

    public GlobalReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public ResponseEntity<List<ReminderResponse>> getRemindersForUser(
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(reminderService.getRemindersForUser(currentUser.getUserId()));
    }
}
