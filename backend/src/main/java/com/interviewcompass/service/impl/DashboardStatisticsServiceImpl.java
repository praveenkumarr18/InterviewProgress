package com.interviewcompass.service.impl;

import com.interviewcompass.dto.DashboardStatisticsResponse;
import com.interviewcompass.entity.ApplicationStatus;
import com.interviewcompass.repository.InterviewExperienceRepository;
import com.interviewcompass.repository.InterviewFeedbackRepository;
import com.interviewcompass.repository.InterviewQuestionRepository;
import com.interviewcompass.repository.InterviewRoundRepository;
import com.interviewcompass.repository.ReminderRepository;
import com.interviewcompass.repository.ResumeRepository;
import com.interviewcompass.service.DashboardStatisticsService;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardStatisticsServiceImpl implements DashboardStatisticsService {

    private final com.interviewcompass.repository.CompanyApplicationRepository companyApplicationRepository;
    private final InterviewExperienceRepository interviewExperienceRepository;
    private final InterviewRoundRepository interviewRoundRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final InterviewFeedbackRepository interviewFeedbackRepository;
    private final ResumeRepository resumeRepository;
    private final ReminderRepository reminderRepository;

    public DashboardStatisticsServiceImpl(com.interviewcompass.repository.CompanyApplicationRepository companyApplicationRepository,
                                          InterviewExperienceRepository interviewExperienceRepository,
                                          InterviewRoundRepository interviewRoundRepository,
                                          InterviewQuestionRepository interviewQuestionRepository,
                                          InterviewFeedbackRepository interviewFeedbackRepository,
                                          ResumeRepository resumeRepository,
                                          ReminderRepository reminderRepository) {
        this.companyApplicationRepository = companyApplicationRepository;
        this.interviewExperienceRepository = interviewExperienceRepository;
        this.interviewRoundRepository = interviewRoundRepository;
        this.interviewQuestionRepository = interviewQuestionRepository;
        this.interviewFeedbackRepository = interviewFeedbackRepository;
        this.resumeRepository = resumeRepository;
        this.reminderRepository = reminderRepository;
    }

    @Override
    public DashboardStatisticsResponse getDashboardStatistics(Long userId) {
        Long ownerId = Objects.requireNonNull(userId, "userId is required");
        if (ownerId <= 0) {
            throw new IllegalArgumentException("userId is required");
        }

        DashboardStatisticsResponse response = new DashboardStatisticsResponse();
        response.setUserId(ownerId);
        response.setTotalCompanies(companyApplicationRepository.countByUserId(ownerId));
        response.setSavedCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.SAVED));
        response.setAppliedCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.APPLIED));
        response.setInterviewScheduledCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.INTERVIEW_SCHEDULED));
        response.setInterviewingCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.INTERVIEWING));
        response.setOfferedCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.OFFERED));
        response.setRejectedCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.REJECTED));
        response.setWithdrawnCompanies(companyApplicationRepository.countByUserIdAndStatus(ownerId, ApplicationStatus.WITHDRAWN));
        response.setTotalExperiences(interviewExperienceRepository.countByCompanyApplication_UserId(ownerId));
        response.setTotalRounds(interviewRoundRepository.countByInterviewExperience_CompanyApplication_UserId(ownerId));
        response.setTotalQuestions(interviewQuestionRepository.countByInterviewRound_InterviewExperience_CompanyApplication_UserId(ownerId));
        response.setTotalFeedbacks(interviewFeedbackRepository.countByInterviewExperience_CompanyApplication_UserId(ownerId));
        response.setTotalResumes(resumeRepository.countByCompanyApplication_UserId(ownerId));
        response.setTotalReminders(reminderRepository.countByInterviewExperience_CompanyApplication_UserId(ownerId));
        response.setUpcomingReminders(reminderRepository.countByInterviewExperience_CompanyApplication_UserIdAndReminderAtAfter(ownerId, LocalDateTime.now()));
        return response;
    }
}
