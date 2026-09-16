package com.interviewcompass.repository;

import com.interviewcompass.entity.Reminder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    long countByInterviewExperience_CompanyApplication_UserId(Long userId);

    long countByInterviewExperience_CompanyApplication_UserIdAndReminderAtAfter(Long userId, java.time.LocalDateTime reminderAt);

    List<Reminder> findAllByInterviewExperienceIdOrderByReminderAtAsc(Long interviewExperienceId);

    List<Reminder> findAllByInterviewExperience_CompanyApplication_UserIdOrderByReminderAtAsc(Long userId);

    Optional<Reminder> findByIdAndInterviewExperienceId(Long id, Long interviewExperienceId);
}
