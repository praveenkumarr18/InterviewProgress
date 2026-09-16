package com.interviewcompass.service;

import com.interviewcompass.dto.CompanyApplicationRequest;
import com.interviewcompass.dto.CompanyApplicationResponse;
import java.util.List;

public interface CompanyApplicationService {

    CompanyApplicationResponse createCompanyApplication(Long userId, CompanyApplicationRequest request);

    CompanyApplicationResponse updateCompanyApplication(Long id, Long userId, CompanyApplicationRequest request);

    CompanyApplicationResponse getCompanyApplicationById(Long id, Long userId);

    List<CompanyApplicationResponse> getCompanyApplications(Long userId);

    void deleteCompanyApplication(Long id, Long userId);
}
