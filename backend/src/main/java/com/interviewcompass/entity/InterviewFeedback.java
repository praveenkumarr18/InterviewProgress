package com.interviewcompass.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "interview_feedbacks")
public class InterviewFeedback extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_experience_id", nullable = false, unique = true)
    private InterviewExperience interviewExperience;

    @Column(length = 1000)
    private String strengths;

    @Column(length = 1000)
    private String weaknesses;

    @Column(length = 1000)
    private String improvementNotes;

    @Column(length = 1000)
    private String interviewerFeedback;

    public InterviewFeedback() {
    }

    public InterviewExperience getInterviewExperience() {
        return interviewExperience;
    }

    public void setInterviewExperience(InterviewExperience interviewExperience) {
        this.interviewExperience = interviewExperience;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getImprovementNotes() {
        return improvementNotes;
    }

    public void setImprovementNotes(String improvementNotes) {
        this.improvementNotes = improvementNotes;
    }

    public String getInterviewerFeedback() {
        return interviewerFeedback;
    }

    public void setInterviewerFeedback(String interviewerFeedback) {
        this.interviewerFeedback = interviewerFeedback;
    }
}
