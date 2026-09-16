# Project Blueprint

## Product Intent

Interview Compass helps students manage the full placement journey from company application to interview feedback and improvement tracking.

## Architecture Approach

The system follows a classic two-tier application design:

- React frontend handles presentation only.
- Spring Boot backend handles authentication, validation, business rules, persistence, file upload, and response shaping.
- MySQL stores all user-owned placement data.

## Request Flow

1. User interacts with a React page.
2. React sends an HTTP request through Axios.
3. Spring Boot controller receives the request.
4. Service layer applies business rules.
5. Repository layer reads or writes MySQL data.
6. Backend returns JSON.
7. React renders the response.

## Why This Structure

This separation keeps UI concerns away from business logic, makes the backend testable, and lets the frontend evolve without rewriting persistence rules.
