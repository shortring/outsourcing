package com.outsourcing.domain.activities.service;

import com.outsourcing.common.dto.PageCondition;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesAllResponse;
import com.outsourcing.domain.activities.dto.response.ActivitiesResponse;
import com.outsourcing.domain.activities.repository.ActivityQueryRepositoryImpl;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ActivitiesService {

    private final ActivityQueryRepositoryImpl queryRepository;
    private final UserRepository userRepository;
    private static final ZoneId KOREA = ZoneId.of("Asia/Seoul");

    // 전체 활동 로그 조회
    @Transactional(readOnly = true)
    public PagedResponse<ActivitiesResponse> getAllActivitiesLog(
            int page,
            int size,
            ActivityType type,
            Long taskId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Instant from = (startDate == null) ? null : startDate.atStartOfDay(KOREA).toInstant();
        Instant to = (endDate == null) ? null : endDate.plusDays(1).atStartOfDay(KOREA).toInstant();

        PageCondition pageCondition = PageCondition.of(page, size);

        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        Page<Activity> activityPage = queryRepository.search(type, taskId, from, to, pageable);

        Page<ActivitiesResponse> response = activityPage.map(ActivitiesResponse::from);

        return PagedResponse.from(response);
    }

    // 내 활동 로그 조회
    @Transactional(readOnly = true)
    public PagedResponse<ActivitiesAllResponse> getAllMyActivitiesLog(
            Long userId,
            int page,
            int size,
            ActivityType type,
            Long taskId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        Instant from = (startDate == null)
                ? null
                : startDate.atStartOfDay(KOREA).toInstant();

        Instant to = (endDate == null)
                ? null
                : endDate.plusDays(1).atStartOfDay(KOREA).toInstant();

        PageCondition pageCondition = PageCondition.of(page, size);
        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        Page<Activity> activityPage = queryRepository.search(user.getId(), type, taskId, from, to, pageable);

        Page<ActivitiesAllResponse> response = activityPage.map(ActivitiesAllResponse::from);

        return PagedResponse.from(response);
    }
}
