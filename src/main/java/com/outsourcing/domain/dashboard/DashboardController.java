package com.outsourcing.domain.dashboard;

import com.outsourcing.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<StatsDashboardResponse>> getStats() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("대시보드 통계 조회 성공",dashboardService.stats(1L)));
    }

    @GetMapping("/tasks/summary")
    public ResponseEntity<ApiResponse<SummaryMyTaskResponse>> getMyTaskSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("내 작업 요약 조회 성공", dashboardService.myTaskSummary(1L)));
    }


    @GetMapping("/tasks/trend/weekly")
    public WeeklyTaskTrendDashboardResponse getWeeklyTaskTrend() {
        return dashboardService.weeklyTaskTrend();
    }

}
