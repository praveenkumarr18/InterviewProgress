package com.interviewcompass.controller;

import com.interviewcompass.dto.DashboardStatisticsResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.DashboardStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardStatisticsController {

    private final DashboardStatisticsService dashboardStatisticsService;

    public DashboardStatisticsController(DashboardStatisticsService dashboardStatisticsService) {
        this.dashboardStatisticsService = dashboardStatisticsService;
    }

    @GetMapping
    public ResponseEntity<DashboardStatisticsResponse> getDashboardStatistics(@org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(dashboardStatisticsService.getDashboardStatistics(currentUser.getUserId()));
    }
}
