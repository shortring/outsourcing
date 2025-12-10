package com.outsourcing.domain.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public StatsDashboardResponse getStats() {
        return dashboardService.stats(1L);
    }

    @GetMapping("/tasks/summary")
    public List<SummaryTaskResponse> getMyTaskSummary() {
        return dashboardService.myTaskSummary();
    }


    @GetMapping("/tasks/trend/weekly")
    public WeeklyTaskTrendDashboardResponse getWeeklyTaskTrend() {
        return dashboardService.weeklyTaskTrend();
    }

}
