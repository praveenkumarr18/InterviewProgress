package com.interviewcompass.service.impl;

import com.interviewcompass.dto.ResumeResponse;
import com.interviewcompass.entity.CompanyApplication;
import com.interviewcompass.entity.Resume;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.mapper.ResumeMapper;
import com.interviewcompass.repository.CompanyApplicationRepository;
import com.interviewcompass.repository.ResumeRepository;
import com.interviewcompass.service.ResumeService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final CompanyApplicationRepository companyApplicationRepository;
    private final Path resumeDirectory;

    public ResumeServiceImpl(ResumeRepository resumeRepository,
                             CompanyApplicationRepository companyApplicationRepository,
                             @Value("${app.storage.resume-directory}") String resumeDirectory) {
        this.resumeRepository = resumeRepository;
        this.companyApplicationRepository = companyApplicationRepository;
        this.resumeDirectory = Paths.get(resumeDirectory);
    }

    @Override
    public ResumeResponse uploadResume(Long companyApplicationId, Long userId, String versionLabel, Boolean active, MultipartFile file) {
        CompanyApplication companyApplication = getCompanyApplicationOrThrow(companyApplicationId, userId);
        Path storedPath = storeFile(file);

        if (Boolean.TRUE.equals(active)) {
            resumeRepository.findAllByCompanyApplicationIdOrderByCreatedAtDesc(companyApplicationId)
                    .forEach(existingResume -> {
                        existingResume.setActive(Boolean.FALSE);
                        resumeRepository.save(existingResume);
                    });
        }

        Resume resume = new Resume();
        resume.setCompanyApplication(companyApplication);
        resume.setFileName(file.getOriginalFilename() == null ? "resume" : file.getOriginalFilename());
        resume.setFilePath(storedPath.toString());
        resume.setFileType(file.getContentType());
        resume.setVersionLabel(versionLabel);
        resume.setActive(Boolean.TRUE.equals(active));
        return ResumeMapper.toResponse(resumeRepository.save(Objects.requireNonNull(resume)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeResponse getResumeById(Long id, Long userId, Long companyApplicationId) {
        Resume resume = getResumeOrThrow(id, userId, companyApplicationId);
        return ResumeMapper.toResponse(resume);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeResponse> getResumes(Long companyApplicationId, Long userId) {
        getCompanyApplicationOrThrow(companyApplicationId, userId);
        return resumeRepository.findAllByCompanyApplicationIdOrderByCreatedAtDesc(companyApplicationId)
                .stream()
                .map(ResumeMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteResume(Long id, Long userId, Long companyApplicationId) {
        Resume resume = getResumeOrThrow(id, userId, companyApplicationId);
        deleteStoredFile(resume.getFilePath());
        resumeRepository.delete(resume);
    }

    private Resume getResumeOrThrow(Long id, Long userId, Long companyApplicationId) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        getCompanyApplicationOrThrow(companyApplicationId, userId);
        return resumeRepository.findByIdAndCompanyApplicationId(id, companyApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found for the given company application"));
    }

    private CompanyApplication getCompanyApplicationOrThrow(Long companyApplicationId, Long userId) {
        if (companyApplicationId == null || companyApplicationId <= 0) {
            throw new IllegalArgumentException("companyApplicationId is required");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }
        return companyApplicationRepository.findByIdAndUserId(companyApplicationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Company application not found for the given user"));
    }

    private Path storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("resume file is required");
        }
        try {
            Files.createDirectories(resumeDirectory);
            String originalName = normalizeFileName(file.getOriginalFilename());
            String storedFileName = UUID.randomUUID() + "-" + originalName;
            Path targetPath = resumeDirectory.resolve(storedFileName);
            Files.write(targetPath, file.getBytes());
            return targetPath;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to store resume file", exception);
        }
    }

    private String normalizeFileName(String originalName) {
        String safeName = originalName == null || originalName.isBlank() ? "resume" : originalName;
        return safeName.replaceAll("\\s+", "_");
    }

    private void deleteStoredFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to delete resume file", exception);
        }
    }
}
