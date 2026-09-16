package com.interviewcompass.repository;

import com.interviewcompass.entity.InterviewExperience;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewExperienceRepository extends JpaRepository<InterviewExperience, Long> {

    long countByCompanyApplication_UserId(Long userId);

    List<InterviewExperience> findAllByCompanyApplicationIdOrderByCreatedAtDesc(Long companyApplicationId);

    Optional<InterviewExperience> findByIdAndCompanyApplicationId(Long id, Long companyApplicationId);
}
