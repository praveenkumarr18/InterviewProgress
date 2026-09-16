package com.interviewcompass.service.impl;

import com.interviewcompass.dto.ReminderRequest;
import com.interviewcompass.dto.ReminderResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.Reminder;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.ReminderMapper;
import com.interviewcompass.repository.InterviewExperienceRepository;
import com.interviewcompass.repository.ReminderRepository;
import com.interviewcompass.service.ReminderService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final InterviewExperienceRepository interviewExperienceRepository;

    public ReminderServiceImpl(ReminderRepository reminderRepository,
                               InterviewExperienceRepository interviewExperienceRepository) {
        this.reminderRepository = reminderRepository;
        this.interviewExperienceRepository = interviewExperienceRepository;
    }

    @Override
    public ReminderResponse createReminder(Long userId, ReminderRequest request) {
        Long interviewExperienceId = Objects.requireNonNull(request.getInterviewExperienceId(), "interviewExperienceId is required");
        InterviewExperience interviewExperience = Objects.requireNonNull(getInterviewExperienceOrThrow(interviewExperienceId, userId));
        Reminder reminder = ReminderMapper.toEntity(request, interviewExperience);
        Reminder savedReminder = reminderRepository.save(Objects.requireNonNull(reminder));
        return ReminderMapper.toResponse(savedReminder);
    }

    @Override
    public ReminderResponse updateReminder(Long id, Long userId, ReminderRequest request) {
        Long reminderId = Objects.requireNonNull(id, "id is required");
        Long interviewExperienceId = Objects.requireNonNull(request.getInterviewExperienceId(), "interviewExperienceId is required");
        Reminder reminder = Objects.requireNonNull(getReminderOrThrow(reminderId, interviewExperienceId, userId));
        ReminderMapper.updateEntity(reminder, request);
        return ReminderMapper.toResponse(reminderRepository.save(Objects.requireNonNull(reminder)));
    }

    @Override
    @Transactional(readOnly = true)
    public ReminderResponse getReminderById(Long id, Long userId, Long interviewExperienceId) {
        return ReminderMapper.toResponse(Objects.requireNonNull(getReminderOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required"),
                Objects.requireNonNull(userId, "userId is required"))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReminderResponse> getReminders(Long interviewExperienceId, Long userId) {
        Long experienceId = Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required");
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(getInterviewExperienceOrThrow(experienceId, ownerId));
        return Objects.requireNonNull(reminderRepository.findAllByInterviewExperienceIdOrderByReminderAtAsc(experienceId))
                .stream()
                .map(ReminderMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteReminder(Long id, Long userId, Long interviewExperienceId) {
        Reminder reminder = Objects.requireNonNull(getReminderOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required"),
                Objects.requireNonNull(userId, "userId is required")));
        reminderRepository.delete(Objects.requireNonNull(reminder));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReminderResponse> getRemindersForUser(Long userId) {
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        return reminderRepository.findAllByInterviewExperience_CompanyApplication_UserIdOrderByReminderAtAsc(ownerId)
                .stream()
                .map(ReminderMapper::toResponse)
                .toList();
    }

    private Reminder getReminderOrThrow(Long id, Long interviewExperienceId, Long userId) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        Objects.requireNonNull(getInterviewExperienceOrThrow(interviewExperienceId, userId));
        return Objects.requireNonNull(reminderRepository.findByIdAndInterviewExperienceId(id, interviewExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found for the given experience")));
    }

    private InterviewExperience getInterviewExperienceOrThrow(Long interviewExperienceId, Long userId) {
        if (interviewExperienceId <= 0) {
            throw new IllegalArgumentException("interviewExperienceId is required");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        return Objects.requireNonNull(interviewExperienceRepository.findById(interviewExperienceId)
                .filter(experience -> experience.getCompanyApplication().getUser().getId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Interview experience not found for the given user")));
    }
}
