package com.interviewcompass.security;

import com.interviewcompass.entity.ApplicationUser;
import com.interviewcompass.exception.AuthenticationFailedException;
import com.interviewcompass.repository.ApplicationUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth",
            "/auth/",
            "/v3/api-docs",
            "/v3/api-docs/",
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/error"
    );

    private final JwtService jwtService;
    private final ApplicationUserRepository applicationUserRepository;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   ApplicationUserRepository applicationUserRepository,
                                   RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtService = jwtService;
        this.applicationUserRepository = applicationUserRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7).trim();
        try {
            if (!jwtService.isTokenValid(token)) {
                throw new AuthenticationFailedException("Invalid or expired token");
            }

            String email = jwtService.extractEmail(token);
            ApplicationUser user = applicationUserRepository.findByEmail(email)
                    .filter(applicationUser -> Boolean.TRUE.equals(applicationUser.getEnabled()))
                    .orElseThrow(() -> new AuthenticationFailedException("Invalid or expired token"));

            AuthenticatedUser principal = new AuthenticatedUser(user.getId(), user.getEmail(), user.getFullName());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    List.of());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (AuthenticationFailedException exception) {
            authenticationEntryPoint.commence(request, response, new org.springframework.security.authentication.BadCredentialsException(exception.getMessage()));
        }
    }
}
