# API Specification

This document will hold the final REST contract for the project.

## Initial API Groups

- Authentication
- Company applications
- Interview experiences
- Interview rounds
- Interview questions
- Feedback
- Resume uploads
- Reminders
- Dashboard statistics

## Phase 3 Company Application Contract

The company application module is the first working CRUD slice.

- `POST /companies` creates a new company application.
- `GET /companies?userId={id}` lists applications owned by one user.
- `GET /companies/{id}?userId={id}` fetches one application for that user.
- `PUT /companies/{id}` updates one application.
- `DELETE /companies/{id}?userId={id}` deletes one application.

### Current Request Shape

The request body includes `userId` temporarily because JWT-based authentication is not added yet.
That field will later be replaced by the authenticated principal once Phase 11 is implemented.

## Phase 4 Interview Experience Contract

Interview experiences are nested under one company application.

- `GET /companies/{companyApplicationId}/experiences?userId={id}` lists experiences for one company application.
- `POST /companies/{companyApplicationId}/experiences` creates one interview experience.
- `GET /companies/{companyApplicationId}/experiences/{id}?userId={id}` fetches one experience.
- `PUT /companies/{companyApplicationId}/experiences/{id}` updates one experience.
- `DELETE /companies/{companyApplicationId}/experiences/{id}?userId={id}` deletes one experience.

### Current Request Shape

The request body includes `userId` temporarily for ownership checks until authentication is added.
The controller injects `companyApplicationId` from the path so clients do not need to duplicate it in the body.

## Phase 5 Interview Round Contract

Interview rounds are nested under one interview experience.

- `GET /experiences/{interviewExperienceId}/rounds?userId={id}` lists rounds for one experience.
- `POST /experiences/{interviewExperienceId}/rounds` creates one round.
- `GET /experiences/{interviewExperienceId}/rounds/{id}?userId={id}` fetches one round.
- `PUT /experiences/{interviewExperienceId}/rounds/{id}` updates one round.
- `DELETE /experiences/{interviewExperienceId}/rounds/{id}?userId={id}` deletes one round.

### Current Request Shape

The request body includes `userId` temporarily for ownership checks until authentication is added.
The controller injects `interviewExperienceId` from the path so clients do not need to duplicate it in the body.

## Phase 6 Interview Question Contract

Interview questions are nested under one interview round.

- `GET /rounds/{interviewRoundId}/questions?userId={id}` lists questions for one round.
- `POST /rounds/{interviewRoundId}/questions` creates one question.
- `GET /rounds/{interviewRoundId}/questions/{id}?userId={id}` fetches one question.
- `PUT /rounds/{interviewRoundId}/questions/{id}` updates one question.
- `DELETE /rounds/{interviewRoundId}/questions/{id}?userId={id}` deletes one question.

### Current Request Shape

The request body includes `userId` temporarily for ownership checks until authentication is added.
The controller injects `interviewRoundId` from the path so clients do not need to duplicate it in the body.

## Phase 7 Interview Feedback Contract

Interview feedback is a one-to-one record nested under one interview experience.

- `GET /experiences/{interviewExperienceId}/feedback?userId={id}` fetches feedback for one experience.
- `POST /experiences/{interviewExperienceId}/feedback` creates feedback for one experience.
- `PUT /experiences/{interviewExperienceId}/feedback` updates feedback for one experience.
- `DELETE /experiences/{interviewExperienceId}/feedback?userId={id}` deletes feedback for one experience.

### Current Request Shape

The request body includes `userId` temporarily for ownership checks until authentication is added.
The controller injects `interviewExperienceId` from the path so clients do not need to duplicate it in the body.

## Phase 8 Resume Upload Contract

Resume versions are stored under one company application.

- `GET /resume?companyApplicationId={id}&userId={id}` lists resume versions for one application.
- `POST /resume` uploads one resume file.
- `GET /resume/{id}?companyApplicationId={id}&userId={id}` fetches one resume record.
- `DELETE /resume/{id}?companyApplicationId={id}&userId={id}` deletes one resume record.

### Current Request Shape

The upload request uses `multipart/form-data` because the backend stores a physical file.
The request carries `companyApplicationId`, `userId`, optional `versionLabel`, optional `active`, and the uploaded `file`.
The backend stores the file on disk and persists its metadata in MySQL.

## Phase 9 Reminder Contract

Reminders are nested under one interview experience.

- `GET /experiences/{interviewExperienceId}/reminders?userId={id}` lists reminders for one experience.
- `POST /experiences/{interviewExperienceId}/reminders` creates one reminder.
- `GET /experiences/{interviewExperienceId}/reminders/{id}?userId={id}` fetches one reminder.
- `PUT /experiences/{interviewExperienceId}/reminders/{id}` updates one reminder.
- `DELETE /experiences/{interviewExperienceId}/reminders/{id}?userId={id}` deletes one reminder.

### Current Request Shape

The request body includes `userId` temporarily for ownership checks until authentication is added.
The controller injects `interviewExperienceId` from the path so clients do not need to duplicate it in the body.

## Phase 10 Dashboard Statistics Contract

Dashboard statistics are derived from persisted user data across all modules.

- `GET /dashboard?userId={id}` returns the dashboard summary for one user.

### What the API Returns

The response is computed from the database and can include total companies, status-based company counts, total experiences, rounds, questions, feedback entries, resume versions, reminder counts, and upcoming reminders.

### Current Request Shape

The dashboard request is now authenticated. The backend reads the current user from the JWT bearer token and returns only that user’s derived counts.

## Phase 11 Authentication Contract

### Public Auth Endpoints

- `POST /auth/register` creates a student account and returns a JWT plus profile data.
- `POST /auth/login` validates credentials and returns a JWT plus profile data.
- `GET /auth/me` returns the currently authenticated user profile.

### Protected Endpoints

All company, experience, round, question, feedback, resume, reminder, and dashboard endpoints now require `Authorization: Bearer <token>`.

### Frontend Communication

The frontend logs in once, stores the returned token, and sends it on every API request in the `Authorization` header. The backend resolves the current user from the token and enforces ownership from that authenticated identity.

## Communication Rule

Frontend must call backend APIs only through Axios. It must not calculate business metrics or directly access the database.
