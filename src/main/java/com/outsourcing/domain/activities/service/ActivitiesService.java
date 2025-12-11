package com.outsourcing.domain.activities.service;

import com.outsourcing.common.aop.CreateLog;
import com.outsourcing.common.entity.Activity;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesResponse;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivitiesService {
    private final ActivityRepository activityRepository;
    private final Logger log = LoggerFactory.getLogger(ActivitiesService.class);

    @Transactional
    public Page<ActivitiesResponse> getAllActivitiesLog(ActivityType type, Long taskId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {

        // 조회 기간 설정
        LocalDateTime startDateTime = startDate == null ? startDate.withYear(1).withMonth(1).withDayOfMonth(1) : startDate;
        LocalDateTime endDateTime = endDate == null ? endDate.withYear(9999).withMonth(12).withDayOfMonth(31) : endDate;

        // 페이징
        Page<Activity> activitiesPage;

        // 설정에 따라 조건 조회
        if (type == null && taskId == null) {
            // 전부 조회
            activitiesPage = activityRepository.findAll(pageable, startDateTime, endDateTime);
        } else if (type == null) {
            // 작업 id로 조건 조회
            activitiesPage = activityRepository.findAllByTaskId(taskId, pageable, startDateTime, endDateTime);
        } else if (taskId == null) {
            // 타입으로 조건 조회
            activitiesPage = activityRepository.findAllByType(taskId, pageable, startDateTime, endDateTime);
        } else {
            // 작업 id && 타입으로 조건 조회
            activitiesPage = activityRepository.findAllByTypeAndTaskId(taskId, pageable, startDateTime, endDateTime);
        }

        // 최종 조회 값 리턴
        return activitiesPage.map(ActivitiesResponse::from);
    }
}
