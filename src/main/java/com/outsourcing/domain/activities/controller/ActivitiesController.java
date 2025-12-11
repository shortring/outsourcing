package com.outsourcing.domain.activities.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesResponse;
import com.outsourcing.domain.activities.service.ActivitiesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activities")
public class ActivitiesController {

    private final ActivitiesService activitiesService;

    // 전체 활동 로그 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ActivitiesResponse>>> getAllActivitiesLog(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ActivityType type,
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));  // 기본 정렬
        Page<ActivitiesResponse> result = activitiesService.getAllActivitiesLog(type, taskId, pageable, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("활동 로그 조회 성공", result));
    }

    // 내 활동 로그 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ActivitiesResponse>>> getAllMyActivitiesLog(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ActivitiesResponse> result = activitiesService.getAllMyActivitiesLog(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("활동 로그 조회 성공", result));
    }
}
