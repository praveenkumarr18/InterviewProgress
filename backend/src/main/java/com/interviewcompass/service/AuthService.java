package com.interviewcompass.service;

import com.interviewcompass.dto.AuthResponse;
import com.interviewcompass.dto.LoginRequest;
import com.interviewcompass.dto.RegisterRequest;
import com.interviewcompass.dto.UserProfileResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserProfileResponse getCurrentUser(Long userId);
}
