package com.interviewcompass.service;

import com.interviewcompass.dto.ResumeResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {

    ResumeResponse uploadResume(Long companyApplicationId, Long userId, String versionLabel, Boolean active, MultipartFile file);

    ResumeResponse getResumeById(Long id, Long userId, Long companyApplicationId);

    List<ResumeResponse> getResumes(Long companyApplicationId, Long userId);

    void deleteResume(Long id, Long userId, Long companyApplicationId);
}
