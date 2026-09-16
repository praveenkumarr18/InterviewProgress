package com.interviewcompass.service.impl;

import com.interviewcompass.dto.CompanyApplicationRequest;
import com.interviewcompass.dto.CompanyApplicationResponse;
import com.interviewcompass.entity.ApplicationUser;
import com.interviewcompass.entity.CompanyApplication;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.CompanyApplicationMapper;
import com.interviewcompass.repository.ApplicationUserRepository;
import com.interviewcompass.repository.CompanyApplicationRepository;
import com.interviewcompass.service.CompanyApplicationService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyApplicationServiceImpl implements CompanyApplicationService {

    private final CompanyApplicationRepository companyApplicationRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public CompanyApplicationServiceImpl(CompanyApplicationRepository companyApplicationRepository,
                                         ApplicationUserRepository applicationUserRepository) {
        this.companyApplicationRepository = companyApplicationRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public CompanyApplicationResponse createCompanyApplication(Long userId, CompanyApplicationRequest request) {
        CompanyApplicationRequest safeRequest = Objects.requireNonNull(request, "request is required");
        ApplicationUser user = Objects.requireNonNull(getUserOrThrow(userId));
        CompanyApplication companyApplication = Objects.requireNonNull(CompanyApplicationMapper.toEntity(safeRequest, user));
        CompanyApplication savedCompanyApplication = companyApplicationRepository.save(Objects.requireNonNull(companyApplication));
        return CompanyApplicationMapper.toResponse(savedCompanyApplication);
    }

    @Override
    public CompanyApplicationResponse updateCompanyApplication(Long id, Long userId, CompanyApplicationRequest request) {
        Long companyApplicationId = Objects.requireNonNull(id, "id is required");
        CompanyApplicationRequest safeRequest = Objects.requireNonNull(request, "request is required");
        CompanyApplication companyApplication = Objects.requireNonNull(getCompanyApplicationOrThrow(companyApplicationId, userId));
        CompanyApplicationMapper.updateEntity(companyApplication, safeRequest);
        return CompanyApplicationMapper.toResponse(companyApplicationRepository.save(Objects.requireNonNull(companyApplication)));
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyApplicationResponse getCompanyApplicationById(Long id, Long userId) {
        return CompanyApplicationMapper.toResponse(Objects.requireNonNull(getCompanyApplicationOrThrow(
                Objects.requireNonNull(id, "id is required"),
            Objects.requireNonNull(userId, "userId is required"))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyApplicationResponse> getCompanyApplications(Long userId) {
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        if (ownerId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        return Objects.requireNonNull(companyApplicationRepository.findAllByUserIdOrderByCreatedAtDesc(ownerId))
                .stream()
                .map(CompanyApplicationMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCompanyApplication(Long id, Long userId) {
        CompanyApplication companyApplication = Objects.requireNonNull(getCompanyApplicationOrThrow(
                Objects.requireNonNull(id, "id is required"),
                Objects.requireNonNull(userId, "userId is required")));
        companyApplicationRepository.delete(Objects.requireNonNull(companyApplication));
    }

    private CompanyApplication getCompanyApplicationOrThrow(Long id, Long userId) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        return Objects.requireNonNull(companyApplicationRepository.findByIdAndUserId(id, userId)
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
