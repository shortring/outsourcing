package com.outsourcing.domain.activities.repository;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<ActivitiesResponse> filter(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Activity> findAllByTypeAndTaskId(ActivityType type, Long taskId, Pageable pageable);

    Page<Activity> findAll(Pageable pageable, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Activity> findAllByTaskId(Long taskId, Pageable pageable, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Activity> findAllByType(Long taskId, Pageable pageable, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Activity> findAllByTypeAndTaskId(Long taskId, Pageable pageable, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Activity> findAllByUserId(Long userId, Pageable pageable);
}
