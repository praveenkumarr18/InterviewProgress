package com.interviewcompass.service.impl;

import com.interviewcompass.dto.InterviewQuestionRequest;
import com.interviewcompass.dto.InterviewQuestionResponse;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewQuestion;
import com.interviewcompass.entity.InterviewRound;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.InterviewQuestionMapper;
import com.interviewcompass.repository.InterviewQuestionRepository;
import com.interviewcompass.repository.InterviewRoundRepository;
import com.interviewcompass.service.InterviewQuestionService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterviewQuestionServiceImpl implements InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewRoundRepository interviewRoundRepository;

    public InterviewQuestionServiceImpl(InterviewQuestionRepository interviewQuestionRepository,
                                        InterviewRoundRepository interviewRoundRepository) {
        this.interviewQuestionRepository = interviewQuestionRepository;
        this.interviewRoundRepository = interviewRoundRepository;
    }

    @Override
    public InterviewQuestionResponse createInterviewQuestion(Long userId, InterviewQuestionRequest request) {
        Long interviewRoundId = Objects.requireNonNull(request.getInterviewRoundId(), "interviewRoundId is required");
        InterviewRound interviewRound = Objects.requireNonNull(getInterviewRoundOrThrow(interviewRoundId, userId));
        InterviewQuestion interviewQuestion = InterviewQuestionMapper.toEntity(request, interviewRound);
        InterviewQuestion savedInterviewQuestion = interviewQuestionRepository.save(Objects.requireNonNull(interviewQuestion));
        return InterviewQuestionMapper.toResponse(savedInterviewQuestion);
    }

    @Override
    public InterviewQuestionResponse updateInterviewQuestion(Long id, Long userId, InterviewQuestionRequest request) {
        Long questionId = Objects.requireNonNull(id, "id is required");
        Long interviewRoundId = Objects.requireNonNull(request.getInterviewRoundId(), "interviewRoundId is required");
        InterviewQuestion interviewQuestion = Objects.requireNonNull(getInterviewQuestionOrThrow(questionId, interviewRoundId, userId));
        InterviewQuestionMapper.updateEntity(interviewQuestion, request);
        return InterviewQuestionMapper.toResponse(interviewQuestionRepository.save(Objects.requireNonNull(interviewQuestion)));
    }

    @Override
    @Transactional(readOnly = true)
    public InterviewQuestionResponse getInterviewQuestionById(Long id, Long userId, Long interviewRoundId) {
        return InterviewQuestionMapper.toResponse(Objects.requireNonNull(getInterviewQuestionOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(interviewRoundId, "interviewRoundId is required"),
                Objects.requireNonNull(userId, "userId is required"))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewQuestionResponse> getInterviewQuestions(Long interviewRoundId, Long userId) {
        Long roundId = Objects.requireNonNull(interviewRoundId, "interviewRoundId is required");
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(getInterviewRoundOrThrow(roundId, ownerId));
        return Objects.requireNonNull(interviewQuestionRepository.findAllByInterviewRoundIdOrderByCreatedAtDesc(roundId))
                .stream()
                .map(InterviewQuestionMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteInterviewQuestion(Long id, Long userId, Long interviewRoundId) {
        InterviewQuestion interviewQuestion = Objects.requireNonNull(getInterviewQuestionOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(interviewRoundId, "interviewRoundId is required"),
                Objects.requireNonNull(userId, "userId is required")));
        interviewQuestionRepository.delete(Objects.requireNonNull(interviewQuestion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewQuestionResponse> searchQuestions(Long userId, String topic, String difficultyLevel, String search, String companyName) {
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        return interviewQuestionRepository.searchQuestions(ownerId, topic, difficultyLevel, search, companyName)
                .stream()
                .map(InterviewQuestionMapper::toResponse)
                .toList();
    }

    private InterviewQuestion getInterviewQuestionOrThrow(Long id, Long interviewRoundId, Long userId) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        Objects.requireNonNull(getInterviewRoundOrThrow(interviewRoundId, userId));
        return Objects.requireNonNull(interviewQuestionRepository.findByIdAndInterviewRoundId(id, interviewRoundId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview question not found for the given round")));
    }

    private InterviewRound getInterviewRoundOrThrow(Long interviewRoundId, Long userId) {
        if (interviewRoundId <= 0) {
            throw new IllegalArgumentException("interviewRoundId is required");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        InterviewRound interviewRound = interviewRoundRepository.findById(interviewRoundId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found"));
        InterviewExperience interviewExperience = interviewRound.getInterviewExperience();
        if (interviewExperience == null || interviewExperience.getCompanyApplication() == null
                || interviewExperience.getCompanyApplication().getUser() == null
                || interviewExperience.getCompanyApplication().getUser().getId() == null
                || !interviewExperience.getCompanyApplication().getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Interview round not found for the given user");
        }
        return interviewRound;
    }
}
