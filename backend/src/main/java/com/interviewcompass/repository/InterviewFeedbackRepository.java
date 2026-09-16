package com.interviewcompass.repository;

import com.interviewcompass.entity.InterviewFeedback;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewFeedbackRepository extends JpaRepository<InterviewFeedback, Long> {

    long countByInterviewExperience_CompanyApplication_UserId(Long userId);

    Optional<InterviewFeedback> findByInterviewExperienceId(Long interviewExperienceId);
}
