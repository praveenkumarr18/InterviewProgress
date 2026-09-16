package com.interviewcompass.service.impl;

import com.interviewcompass.dto.InterviewExperienceRequest;
import com.interviewcompass.dto.InterviewExperienceResponse;
import com.interviewcompass.entity.ApplicationUser;
import com.interviewcompass.entity.CompanyApplication;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.InterviewExperienceMapper;
import com.interviewcompass.repository.ApplicationUserRepository;
import com.interviewcompass.repository.CompanyApplicationRepository;
import com.interviewcompass.repository.InterviewExperienceRepository;
import com.interviewcompass.service.InterviewExperienceService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterviewExperienceServiceImpl implements InterviewExperienceService {

    private final InterviewExperienceRepository interviewExperienceRepository;
    private final CompanyApplicationRepository companyApplicationRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public InterviewExperienceServiceImpl(InterviewExperienceRepository interviewExperienceRepository,
                                          CompanyApplicationRepository companyApplicationRepository,
                                          ApplicationUserRepository applicationUserRepository) {
        this.interviewExperienceRepository = interviewExperienceRepository;
        this.companyApplicationRepository = companyApplicationRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public InterviewExperienceResponse createInterviewExperience(Long userId, InterviewExperienceRequest request) {
        Long companyApplicationId = Objects.requireNonNull(request.getCompanyApplicationId(), "companyApplicationId is required");
        ApplicationUser user = getUserOrThrow(userId);
        CompanyApplication companyApplication = getCompanyApplicationOrThrow(companyApplicationId, user.getId());
        InterviewExperience interviewExperience = InterviewExperienceMapper.toEntity(request, companyApplication);
        InterviewExperience savedInterviewExperience = interviewExperienceRepository.save(Objects.requireNonNull(interviewExperience));
        return InterviewExperienceMapper.toResponse(savedInterviewExperience);
    }

    @Override
    public InterviewExperienceResponse updateInterviewExperience(Long id, Long userId, InterviewExperienceRequest request) {
        Long interviewExperienceId = Objects.requireNonNull(id, "id is required");
        Long companyApplicationId = Objects.requireNonNull(request.getCompanyApplicationId(), "companyApplicationId is required");
        InterviewExperience interviewExperience = getInterviewExperienceOrThrow(interviewExperienceId, companyApplicationId, userId);
        InterviewExperienceMapper.updateEntity(interviewExperience, request);
        return InterviewExperienceMapper.toResponse(interviewExperienceRepository.save(Objects.requireNonNull(interviewExperience)));
    }

    @Override
    @Transactional(readOnly = true)
    public InterviewExperienceResponse getInterviewExperienceById(Long id, Long userId, Long companyApplicationId) {
        return InterviewExperienceMapper.toResponse(getInterviewExperienceOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(companyApplicationId, "companyApplicationId is required"),
                Objects.requireNonNull(userId, "userId is required")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewExperienceResponse> getInterviewExperiences(Long companyApplicationId, Long userId) {
        Long applicationId = Objects.requireNonNull(companyApplicationId, "companyApplicationId is required");
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        getCompanyApplicationOrThrow(applicationId, ownerId);
        return Objects.requireNonNull(interviewExperienceRepository.findAllByCompanyApplicationIdOrderByCreatedAtDesc(applicationId))
                .stream()
                .map(InterviewExperienceMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteInterviewExperience(Long id, Long userId, Long companyApplicationId) {
        InterviewExperience interviewExperience = getInterviewExperienceOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(companyApplicationId, "companyApplicationId is required"),
                Objects.requireNonNull(userId, "userId is required"));
        interviewExperienceRepository.delete(Objects.requireNonNull(interviewExperience));
    }

    private InterviewExperience getInterviewExperienceOrThrow(Long id, Long companyApplicationId, Long userId) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        getCompanyApplicationOrThrow(companyApplicationId, userId);
        return Objects.requireNonNull(interviewExperienceRepository.findByIdAndCompanyApplicationId(id, companyApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview experience not found for the given company application")));
    }

    private CompanyApplication getCompanyApplicationOrThrow(Long companyApplicationId, Long userId) {
        if (companyApplicationId <= 0) {
            throw new IllegalArgumentException("companyApplicationId is required");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        return Objects.requireNonNull(companyApplicationRepository.findByIdAndUserId(companyApplicationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Company application not found for the given user")));
    }

    private ApplicationUser getUserOrThrow(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        return Objects.requireNonNull(applicationUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }
}
