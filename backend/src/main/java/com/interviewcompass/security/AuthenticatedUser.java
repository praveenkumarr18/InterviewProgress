package com.interviewcompass.security;

public class AuthenticatedUser {

    private final Long userId;
    private final String email;
    private final String fullName;

    public AuthenticatedUser(Long userId, String email, String fullName) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}
