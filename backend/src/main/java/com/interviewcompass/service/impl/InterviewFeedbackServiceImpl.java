package com.interviewcompass.service.impl;

import com.interviewcompass.dto.InterviewFeedbackRequest;
import com.interviewcompass.dto.InterviewFeedbackResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewFeedback;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.InterviewFeedbackMapper;
import com.interviewcompass.repository.InterviewExperienceRepository;
import com.interviewcompass.repository.InterviewFeedbackRepository;
import com.interviewcompass.service.InterviewFeedbackService;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterviewFeedbackServiceImpl implements InterviewFeedbackService {

    private final InterviewFeedbackRepository interviewFeedbackRepository;
    private final InterviewExperienceRepository interviewExperienceRepository;

    public InterviewFeedbackServiceImpl(InterviewFeedbackRepository interviewFeedbackRepository,
                                        InterviewExperienceRepository interviewExperienceRepository) {
        this.interviewFeedbackRepository = interviewFeedbackRepository;
        this.interviewExperienceRepository = interviewExperienceRepository;
    }

    @Override
    public InterviewFeedbackResponse createInterviewFeedback(Long userId, InterviewFeedbackRequest request) {
        Long interviewExperienceId = Objects.requireNonNull(request.getInterviewExperienceId(), "interviewExperienceId is required");
        InterviewExperience interviewExperience = Objects.requireNonNull(getInterviewExperienceOrThrow(interviewExperienceId, userId));
        InterviewFeedback interviewFeedback = InterviewFeedbackMapper.toEntity(request, interviewExperience);
        InterviewFeedback savedInterviewFeedback = interviewFeedbackRepository.save(Objects.requireNonNull(interviewFeedback));
        return InterviewFeedbackMapper.toResponse(savedInterviewFeedback);
    }

    @Override
    public InterviewFeedbackResponse updateInterviewFeedback(Long interviewExperienceId, Long userId, InterviewFeedbackRequest request) {
        Long experienceId = Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required");
        InterviewExperience interviewExperience = Objects.requireNonNull(getInterviewExperienceOrThrow(experienceId, userId));
        InterviewFeedback interviewFeedback = Objects.requireNonNull(interviewFeedbackRepository.findByInterviewExperienceId(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview feedback not found for the given experience")));
        if (!interviewFeedback.getInterviewExperience().getId().equals(interviewExperience.getId())) {
            throw new ResourceNotFoundException("Interview feedback not found for the given experience");
        }
        InterviewFeedbackMapper.updateEntity(interviewFeedback, request);
        return InterviewFeedbackMapper.toResponse(interviewFeedbackRepository.save(Objects.requireNonNull(interviewFeedback)));
    }

    @Override
    @Transactional(readOnly = true)
    public InterviewFeedbackResponse getInterviewFeedbackByExperienceId(Long interviewExperienceId, Long userId) {
        Long experienceId = Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required");
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(getInterviewExperienceOrThrow(experienceId, ownerId));
        InterviewFeedback interviewFeedback = interviewFeedbackRepository.findByInterviewExperienceId(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview feedback not found for the given experience"));
        return InterviewFeedbackMapper.toResponse(interviewFeedback);
    }

    @Override
    public void deleteInterviewFeedback(Long interviewExperienceId, Long userId) {
        Long experienceId = Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required");
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(getInterviewExperienceOrThrow(experienceId, ownerId));
        InterviewFeedback interviewFeedback = interviewFeedbackRepository.findByInterviewExperienceId(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview feedback not found for the given experience"));
        interviewFeedbackRepository.delete(Objects.requireNonNull(interviewFeedback));
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
