package com.outsourcing.domain.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard/")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsResponse getStats() {
        return new DashboardStatsResponse();
    }

    @GetMapping("/tasks/summary")
    public DashboardMyTaskSummaryResponse getMyTaskSummary() {
        return new DashboardMyTaskSummaryResponse();
    }


    @GetMapping("/tasks/trend/weekly")
    public DashboardWeeklyTaskTrendResponse getWeeklyTaskTrend() {
        return new DashboardWeeklyTaskTrendResponse();
    }

}
