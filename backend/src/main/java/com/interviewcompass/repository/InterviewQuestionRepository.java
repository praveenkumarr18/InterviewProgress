package com.interviewcompass.repository;

import com.interviewcompass.entity.InterviewQuestion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    long countByInterviewRound_InterviewExperience_CompanyApplication_UserId(Long userId);

    List<InterviewQuestion> findAllByInterviewRoundIdOrderByCreatedAtDesc(Long interviewRoundId);

    Optional<InterviewQuestion> findByIdAndInterviewRoundId(Long id, Long interviewRoundId);

    @Query("SELECT iq FROM InterviewQuestion iq WHERE " +
           "iq.interviewRound.interviewExperience.companyApplication.user.id = :userId " +
           "AND (:topic IS NULL OR :topic = '' OR LOWER(iq.topic) LIKE LOWER(CONCAT('%', :topic, '%'))) " +
           "AND (:difficultyLevel IS NULL OR :difficultyLevel = '' OR iq.difficultyLevel = :difficultyLevel) " +
           "AND (:search IS NULL OR :search = '' OR LOWER(iq.questionText) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:companyName IS NULL OR :companyName = '' OR LOWER(iq.interviewRound.interviewExperience.companyApplication.companyName) LIKE LOWER(CONCAT('%', :companyName, '%')))" +
           "ORDER BY iq.createdAt DESC")
    List<InterviewQuestion> searchQuestions(
        @Param("userId") Long userId,
        @Param("topic") String topic,
        @Param("difficultyLevel") String difficultyLevel,
        @Param("search") String search,
        @Param("companyName") String companyName
    );
}
