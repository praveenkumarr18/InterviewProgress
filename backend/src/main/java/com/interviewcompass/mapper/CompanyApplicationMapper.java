package com.interviewcompass.mapper;

import com.interviewcompass.dto.CompanyApplicationRequest;
import com.interviewcompass.dto.CompanyApplicationResponse;
import com.interviewcompass.entity.ApplicationStatus;
import com.interviewcompass.entity.ApplicationUser;
import com.interviewcompass.entity.CompanyApplication;
import java.util.Locale;
import org.springframework.lang.NonNull;

public final class CompanyApplicationMapper {

    private CompanyApplicationMapper() {
    }

    @NonNull
    public static CompanyApplication toEntity(@NonNull CompanyApplicationRequest request, @NonNull ApplicationUser user) {
        CompanyApplication companyApplication = new CompanyApplication();
        companyApplication.setUser(user);
        companyApplication.setCompanyName(request.getCompanyName());
        companyApplication.setRoleName(request.getRoleName());
        companyApplication.setLocation(request.getLocation());
        companyApplication.setApplicationDate(request.getApplicationDate());
        companyApplication.setStatus(parseStatus(request.getStatus()));
        companyApplication.setSource(request.getSource());
        companyApplication.setNotes(request.getNotes());
        companyApplication.setCompanyUrl(request.getCompanyUrl());
        return companyApplication;
    }

    public static void updateEntity(@NonNull CompanyApplication companyApplication, @NonNull CompanyApplicationRequest request) {
        companyApplication.setCompanyName(request.getCompanyName());
        companyApplication.setRoleName(request.getRoleName());
        companyApplication.setLocation(request.getLocation());
        companyApplication.setApplicationDate(request.getApplicationDate());
        companyApplication.setStatus(parseStatus(request.getStatus()));
        companyApplication.setSource(request.getSource());
        companyApplication.setNotes(request.getNotes());
        companyApplication.setCompanyUrl(request.getCompanyUrl());
    }

    @NonNull
    public static CompanyApplicationResponse toResponse(@NonNull CompanyApplication companyApplication) {
        CompanyApplicationResponse response = new CompanyApplicationResponse();
        response.setId(companyApplication.getId());
        response.setUserId(companyApplication.getUser().getId());
        response.setCompanyName(companyApplication.getCompanyName());
        response.setRoleName(companyApplication.getRoleName());
        response.setLocation(companyApplication.getLocation());
        response.setApplicationDate(companyApplication.getApplicationDate());
        response.setStatus(companyApplication.getStatus().name());
        response.setSource(companyApplication.getSource());
        response.setNotes(companyApplication.getNotes());
        response.setCompanyUrl(companyApplication.getCompanyUrl());
        response.setCreatedAt(companyApplication.getCreatedAt());
        response.setUpdatedAt(companyApplication.getUpdatedAt());
        return response;
    }

    private static ApplicationStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return ApplicationStatus.SAVED;
        }
        return ApplicationStatus.valueOf(status.trim().toUpperCase(Locale.ROOT));
    }
}
