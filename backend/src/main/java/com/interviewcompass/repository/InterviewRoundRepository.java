package com.interviewcompass.repository;

import com.interviewcompass.entity.InterviewRound;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Long> {

    long countByInterviewExperience_CompanyApplication_UserId(Long userId);

    List<InterviewRound> findAllByInterviewExperienceIdOrderByRoundNumberAsc(Long interviewExperienceId);

    Optional<InterviewRound> findByIdAndInterviewExperienceId(Long id, Long interviewExperienceId);
}
