package com.outsourcing.domain.activities.repository;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.domain.activities.dto.ActivityType;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @NonNull
    Page<Activity> findAll(@NonNull Pageable pageable);

    Page<Activity> findAllByTaskId(Long taskId, Pageable pageable, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Activity> findAllByType(ActivityType type, Pageable pageable);

    Page<Activity> findAllByTypeAndTaskId(ActivityType type, Long taskId, Pageable pageable, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<Activity> findAllByUserId(Long userId, Pageable pageable);
}
