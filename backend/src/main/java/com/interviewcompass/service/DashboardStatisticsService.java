package com.interviewcompass.service;

import com.interviewcompass.dto.DashboardStatisticsResponse;

public interface DashboardStatisticsService {

    DashboardStatisticsResponse getDashboardStatistics(Long userId);
}
