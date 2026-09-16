package com.interviewcompass.mapper;

import com.interviewcompass.dto.ResumeResponse;
import com.interviewcompass.entity.Resume;

public final class ResumeMapper {

    private ResumeMapper() {
    }

    public static ResumeResponse toResponse(Resume resume) {
        ResumeResponse response = new ResumeResponse();
        response.setId(resume.getId());
        response.setCompanyApplicationId(resume.getCompanyApplication().getId());
        response.setFileName(resume.getFileName());
        response.setFilePath(resume.getFilePath());
        response.setFileType(resume.getFileType());
        response.setVersionLabel(resume.getVersionLabel());
        response.setActive(resume.getActive());
        response.setCreatedAt(resume.getCreatedAt());
        response.setUpdatedAt(resume.getUpdatedAt());
        return response;
    }
}
