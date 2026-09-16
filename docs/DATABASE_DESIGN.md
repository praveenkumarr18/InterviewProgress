# Database Design

## Core Entities

- User
- CompanyApplication
- InterviewExperience
- InterviewRound
- InterviewQuestion
- InterviewFeedback
- Resume
- Reminder

## Table Intent

- `users` stores student account and profile data.
- `company_applications` stores each company application owned by a user.
- `interview_experiences` stores the overall interview journey for one application.
- `interview_rounds` stores round-by-round details under one experience.
- `interview_questions` stores questions asked in each round.
- `interview_feedbacks` stores one final feedback record per experience.
- `resumes` stores uploaded resume versions for one application.
- `reminders` stores reminder events linked to one interview experience.

## Key Fields

- User: full name, email, password hash, college details, graduation year.
- CompanyApplication: company name, role, application date, status, notes.
- InterviewExperience: interview date, mode, summary, outcome, self reflection.
- InterviewRound: round number, type, title, date, outcome, notes.
- InterviewQuestion: question text, suggested answer, topic, difficulty.
- InterviewFeedback: strengths, weaknesses, improvement notes, interviewer feedback.
- Resume: file name, path, type, version label, active flag.
- Reminder: title, message, reminder time, reminder status.

## Relationship Model

- One User can own many CompanyApplication records.
- One CompanyApplication can have many InterviewExperience records.
- One InterviewExperience can have many InterviewRound records.
- One InterviewRound can have many InterviewQuestion records.
- One InterviewExperience can have one InterviewFeedback record.
- One CompanyApplication can have many Resume records.
- One InterviewExperience can have many Reminder records.

## Design Rule

All dashboard data must be derived from persisted records, not from hardcoded values.

## Why These Relationships Matter

They preserve the placement journey as a navigable chain: user to application, application to experience, experience to rounds, and rounds to questions. That lets the dashboard, search, and improvement tracking query real history instead of disconnected tables.
