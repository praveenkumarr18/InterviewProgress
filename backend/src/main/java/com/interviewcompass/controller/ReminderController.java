package com.interviewcompass.controller;

import com.interviewcompass.dto.ReminderRequest;
import com.interviewcompass.dto.ReminderResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.ReminderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experiences/{interviewExperienceId}/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping
    public ResponseEntity<ReminderResponse> createReminder(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody ReminderRequest request) {
        request.setInterviewExperienceId(interviewExperienceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.createReminder(currentUser.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<ReminderResponse>> getReminders(
            @PathVariable Long interviewExperienceId,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(reminderService.getReminders(interviewExperienceId, currentUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponse> getReminderById(
            @PathVariable Long interviewExperienceId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(reminderService.getReminderById(id, currentUser.getUserId(), interviewExperienceId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(
            @PathVariable Long interviewExperienceId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody ReminderRequest request) {
        request.setInterviewExperienceId(interviewExperienceId);
        return ResponseEntity.ok(reminderService.updateReminder(id, currentUser.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(
            @PathVariable Long interviewExperienceId,
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        reminderService.deleteReminder(id, currentUser.getUserId(), interviewExperienceId);
        return ResponseEntity.noContent().build();
    }
}
