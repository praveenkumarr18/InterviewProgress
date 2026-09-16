package com.interviewcompass.service.impl;

import com.interviewcompass.dto.AuthResponse;
import com.interviewcompass.dto.LoginRequest;
import com.interviewcompass.dto.RegisterRequest;
import com.interviewcompass.dto.UserProfileResponse;
import com.interviewcompass.entity.ApplicationUser;
import com.interviewcompass.exception.AuthenticationFailedException;
import com.interviewcompass.exception.DuplicateResourceException;
import com.interviewcompass.exception.ResourceNotFoundException;
import com.interviewcompass.repository.ApplicationUserRepository;
import com.interviewcompass.security.JwtService;
import com.interviewcompass.service.AuthService;
import java.util.Locale;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(ApplicationUserRepository applicationUserRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        RegisterRequest safeRequest = Objects.requireNonNull(request, "request is required");
        String email = normalizeEmail(Objects.requireNonNull(safeRequest.getEmail(), "email is required"));
        if (applicationUserRepository.findByEmail(email).isPresent()) {
            throw new DuplicateResourceException("An account already exists for this email");
        }

        ApplicationUser user = new ApplicationUser();
        user.setFullName(safeRequest.getFullName().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(safeRequest.getPassword()));
        user.setPhoneNumber(safeRequest.getPhoneNumber());
        user.setCollegeName(safeRequest.getCollegeName());
        user.setBranch(safeRequest.getBranch());
        user.setGraduationYear(safeRequest.getGraduationYear());
        user.setEnabled(Boolean.TRUE);

        ApplicationUser savedUser = applicationUserRepository.save(user);
        return new AuthResponse(jwtService.generateToken(savedUser), toProfile(savedUser));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        LoginRequest safeRequest = Objects.requireNonNull(request, "request is required");
        String email = normalizeEmail(Objects.requireNonNull(safeRequest.getEmail(), "email is required"));
        ApplicationUser user = applicationUserRepository.findByEmail(email)
            .filter(applicationUser -> Boolean.TRUE.equals(applicationUser.getEnabled()))
                .orElseThrow(() -> new AuthenticationFailedException("Invalid email or password"));

        if (!passwordEncoder.matches(safeRequest.getPassword(), user.getPasswordHash())) {
            throw new AuthenticationFailedException("Invalid email or password");
        }

        return new AuthResponse(jwtService.generateToken(user), toProfile(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUser(Long userId) {
        Long currentUserId = Objects.requireNonNull(userId, "userId is required");
        ApplicationUser user = applicationUserRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toProfile(user);
    }

    private UserProfileResponse toProfile(ApplicationUser user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setUserId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setCollegeName(user.getCollegeName());
        response.setBranch(user.getBranch());
        response.setGraduationYear(user.getGraduationYear());
        return response;
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
