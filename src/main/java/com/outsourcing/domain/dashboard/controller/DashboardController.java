package com.outsourcing.domain.dashboard.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.dashboard.dto.StatsDashboardResponse;
import com.outsourcing.domain.dashboard.dto.SummaryMyTaskResponse;
import com.outsourcing.domain.dashboard.dto.WeeklyTaskTrendDashboardResponse;
import com.outsourcing.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    //대시보드 통계 조회
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<StatsDashboardResponse>> getStatsApi(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("대시보드 통계 조회 성공", dashboardService.stats(userDetails.getUserId())));
    }
    //내 작업 요약 조회
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<SummaryMyTaskResponse>> getMyTaskSummaryApi(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("내 작업 요약 조회 성공", dashboardService.myTaskSummary(userDetails.getUserId())));
    }
    //주간 작업 추세 조회
    @GetMapping("/weekly-trend")
    public ResponseEntity<ApiResponse<List<WeeklyTaskTrendDashboardResponse>>> getWeeklyTaskTrendApi() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("주간 작업 추세 조회 성공", dashboardService.weeklyTaskTrend()));
    }

}
