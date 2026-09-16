package com.interviewcompass.repository;

import com.interviewcompass.entity.CompanyApplication;
import com.interviewcompass.entity.ApplicationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyApplicationRepository extends JpaRepository<CompanyApplication, Long> {

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, ApplicationStatus status);

    List<CompanyApplication> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<CompanyApplication> findByIdAndUserId(Long id, Long userId);
}
