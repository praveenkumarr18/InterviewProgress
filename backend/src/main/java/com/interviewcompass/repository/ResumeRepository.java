package com.interviewcompass.repository;

import com.interviewcompass.entity.Resume;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    long countByCompanyApplication_UserId(Long userId);

    List<Resume> findAllByCompanyApplicationIdOrderByCreatedAtDesc(Long companyApplicationId);

    Optional<Resume> findByIdAndCompanyApplicationId(Long id, Long companyApplicationId);
}
