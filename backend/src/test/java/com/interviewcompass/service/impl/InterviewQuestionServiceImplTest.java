package com.interviewcompass.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.interviewcompass.dto.InterviewQuestionRequest;
import com.interviewcompass.dto.InterviewQuestionResponse;
import com.interviewcompass.entity.ApplicationUser;
import com.interviewcompass.entity.CompanyApplication;
import com.interviewcompass.entity.InterviewExperience;
import com.interviewcompass.entity.InterviewQuestion;
import com.interviewcompass.entity.InterviewRound;
import com.interviewcompass.entity.InterviewRoundType;
import com.interviewcompass.repository.InterviewQuestionRepository;
import com.interviewcompass.repository.InterviewRoundRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InterviewQuestionServiceImplTest {

    @Mock
    private InterviewQuestionRepository interviewQuestionRepository;

    @Mock
    private InterviewRoundRepository interviewRoundRepository;

    @InjectMocks
    private InterviewQuestionServiceImpl interviewQuestionService;

    private ApplicationUser user;
    private CompanyApplication company;
    private InterviewExperience experience;
    private InterviewRound round;
    private InterviewQuestion question;

    @BeforeEach
    void setUp() {
        user = new ApplicationUser();
        user.setId(1L);

        company = new CompanyApplication();
        company.setId(1L);
        company.setUser(user);
        company.setCompanyName("Google");

        experience = new InterviewExperience();
        experience.setId(1L);
        experience.setCompanyApplication(company);

        round = new InterviewRound();
        round.setId(1L);
        round.setInterviewExperience(experience);
        round.setRoundNumber(1);
        round.setRoundType(InterviewRoundType.TECHNICAL);
        round.setRoundTitle("Coding Round 1");

        question = new InterviewQuestion();
        question.setId(1L);
        question.setInterviewRound(round);
        question.setQuestionText("How do you reverse a linked list?");
        question.setTopic("Data Structures");
        question.setDifficultyLevel("Medium");
    }

    @Test
    void testCreateInterviewQuestion() {
        InterviewQuestionRequest request = new InterviewQuestionRequest();
        request.setInterviewRoundId(1L);
        request.setQuestionText("How do you reverse a linked list?");
        request.setTopic("Data Structures");
        request.setDifficultyLevel("Medium");

        when(interviewRoundRepository.findById(1L)).thenReturn(Optional.of(round));
        when(interviewQuestionRepository.save(any(InterviewQuestion.class))).thenReturn(question);

        InterviewQuestionResponse response = interviewQuestionService.createInterviewQuestion(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("How do you reverse a linked list?", response.getQuestionText());
        assertEquals("Google", response.getCompanyName());
        assertEquals(1, response.getRoundNumber());

        verify(interviewRoundRepository, times(1)).findById(1L);
        verify(interviewQuestionRepository, times(1)).save(any(InterviewQuestion.class));
    }

    @Test
    void testGetInterviewQuestions() {
        List<InterviewQuestion> questions = List.of(question);
        when(interviewRoundRepository.findById(1L)).thenReturn(Optional.of(round));
        when(interviewQuestionRepository.findAllByInterviewRoundIdOrderByCreatedAtDesc(1L)).thenReturn(questions);

        List<InterviewQuestionResponse> responses = interviewQuestionService.getInterviewQuestions(1L, 1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("How do you reverse a linked list?", responses.get(0).getQuestionText());

        verify(interviewRoundRepository, times(1)).findById(1L);
        verify(interviewQuestionRepository, times(1)).findAllByInterviewRoundIdOrderByCreatedAtDesc(1L);
    }

    @Test
    void testSearchQuestions() {
        List<InterviewQuestion> questions = List.of(question);
        when(interviewQuestionRepository.searchQuestions(1L, "Data Structures", "Medium", "reverse", "Google"))
                .thenReturn(questions);

        List<InterviewQuestionResponse> responses = interviewQuestionService.searchQuestions(
                1L, "Data Structures", "Medium", "reverse", "Google");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Google", responses.get(0).getCompanyName());
        assertEquals("How do you reverse a linked list?", responses.get(0).getQuestionText());

        verify(interviewQuestionRepository, times(1))
                .searchQuestions(1L, "Data Structures", "Medium", "reverse", "Google");
    }

    @Test
    void testDeleteInterviewQuestion() {
        when(interviewRoundRepository.findById(1L)).thenReturn(Optional.of(round));
        when(interviewQuestionRepository.findByIdAndInterviewRoundId(1L, 1L)).thenReturn(Optional.of(question));
        doNothing().when(interviewQuestionRepository).delete(question);

        assertDoesNotThrow(() -> interviewQuestionService.deleteInterviewQuestion(1L, 1L, 1L));

        verify(interviewQuestionRepository, times(1)).delete(question);
    }
}
