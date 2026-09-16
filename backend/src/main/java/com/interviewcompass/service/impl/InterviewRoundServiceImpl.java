package com.interviewcompass.service.impl;

import com.interviewcompass.dto.InterviewRoundRequest;
import com.interviewcompass.dto.InterviewRoundResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewRound;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.InterviewRoundMapper;
import com.interviewcompass.repository.InterviewExperienceRepository;
import com.interviewcompass.repository.InterviewRoundRepository;
import com.interviewcompass.service.InterviewRoundService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterviewRoundServiceImpl implements InterviewRoundService {

    private final InterviewRoundRepository interviewRoundRepository;
    private final InterviewExperienceRepository interviewExperienceRepository;

    public InterviewRoundServiceImpl(InterviewRoundRepository interviewRoundRepository,
                                     InterviewExperienceRepository interviewExperienceRepository) {
        this.interviewRoundRepository = interviewRoundRepository;
        this.interviewExperienceRepository = interviewExperienceRepository;
    }

    @Override
    public InterviewRoundResponse createInterviewRound(Long userId, InterviewRoundRequest request) {
        Long interviewExperienceId = Objects.requireNonNull(request.getInterviewExperienceId(), "interviewExperienceId is required");
        InterviewExperience interviewExperience = Objects.requireNonNull(getInterviewExperienceOrThrow(interviewExperienceId, userId));
        InterviewRound interviewRound = InterviewRoundMapper.toEntity(request, interviewExperience);
        InterviewRound savedInterviewRound = interviewRoundRepository.save(Objects.requireNonNull(interviewRound));
        return InterviewRoundMapper.toResponse(savedInterviewRound);
    }

    @Override
    public InterviewRoundResponse updateInterviewRound(Long id, Long userId, InterviewRoundRequest request) {
        Long interviewRoundId = Objects.requireNonNull(id, "id is required");
        Long interviewExperienceId = Objects.requireNonNull(request.getInterviewExperienceId(), "interviewExperienceId is required");
        InterviewRound interviewRound = Objects.requireNonNull(getInterviewRoundOrThrow(interviewRoundId, interviewExperienceId, userId));
        InterviewRoundMapper.updateEntity(interviewRound, request);
        return InterviewRoundMapper.toResponse(interviewRoundRepository.save(Objects.requireNonNull(interviewRound)));
    }

    @Override
    @Transactional(readOnly = true)
    public InterviewRoundResponse getInterviewRoundById(Long id, Long userId, Long interviewExperienceId) {
        return InterviewRoundMapper.toResponse(Objects.requireNonNull(getInterviewRoundOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required"),
            Objects.requireNonNull(userId, "userId is required"))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewRoundResponse> getInterviewRounds(Long interviewExperienceId, Long userId) {
        Long experienceId = Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required");
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(getInterviewExperienceOrThrow(experienceId, ownerId));
        return Objects.requireNonNull(interviewRoundRepository.findAllByInterviewExperienceIdOrderByRoundNumberAsc(experienceId))
                .stream()
                .map(InterviewRoundMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteInterviewRound(Long id, Long userId, Long interviewExperienceId) {
        InterviewRound interviewRound = Objects.requireNonNull(getInterviewRoundOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(interviewExperienceId, "interviewExperienceId is required"),
            Objects.requireNonNull(userId, "userId is required")));
        interviewRoundRepository.delete(Objects.requireNonNull(interviewRound));
    }

    private InterviewRound getInterviewRoundOrThrow(Long id, Long interviewExperienceId, Long userId) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        Objects.requireNonNull(getInterviewExperienceOrThrow(interviewExperienceId, userId));
        return Objects.requireNonNull(interviewRoundRepository.findByIdAndInterviewExperienceId(id, interviewExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found for the given experience")));
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
