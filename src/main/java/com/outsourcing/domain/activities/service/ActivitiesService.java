package com.outsourcing.domain.activities.service;

import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesResponse;
import com.outsourcing.domain.activities.repository.ActivityQueryRepositoryImpl;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.outsourcing.common.dto.PageCondition;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ActivitiesService {

    private final ActivityQueryRepositoryImpl queryRepository;
    private final UserRepository userRepository;
    private static final ZoneId KOREA = ZoneId.of("Asia/Seoul");

    @Transactional(readOnly = true)
    public PagedResponse<ActivitiesResponse> getAllActivitiesLog(
            int page,
            int size,
            ActivityType type,
            Long taskId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        // 1. 시간 정규화
        Instant from = (startDate==null) // startDate null 이면
                ? null
                : startDate.atStartOfDay(KOREA).toInstant(); //
        Instant to = (endDate == null)
                ? null
                : endDate.plusDays(1).atStartOfDay(KOREA).toInstant();

        // 2. 페이지 정규화
        PageCondition pageCondition = PageCondition.of(page, size);

        // 3. Pageable : Springframework
        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        // 4. 검색 결과
        Page<Activity> activityPage = queryRepository.search(type, taskId, from, to, pageable);

        // 5. response 객체 생성
        Page<ActivitiesResponse> response = activityPage.map(ActivitiesResponse::from);

        // 6. 공통 페이지 응답 객체.
        return PagedResponse.from(response);
    }

    @Transactional(readOnly = true)
    public PagedResponse<ActivitiesResponse> getAllMyActivitiesLog(
            Long userId,
            int page,
            int size,
            ActivityType type,
            Long taskId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        // 1. 유저 객체 생성
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        // 2. 시간 정규화
        Instant from = (startDate==null) // startDate null 이면
                ? null
                : startDate.atStartOfDay(KOREA).toInstant(); //
        Instant to = (endDate == null)
                ? null
                : endDate.plusDays(1).atStartOfDay(KOREA).toInstant();

        // 3. 페이지 정규화
        PageCondition pageCondition = PageCondition.of(page, size);
        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        // 4. 서칭
        Page<Activity> activityPage = queryRepository.search(user.getId(), type, taskId, from, to, pageable);

        // 5. response
        Page<ActivitiesResponse> response = activityPage.map(ActivitiesResponse::from);

        // 6. return
        return PagedResponse.from(response);
    }
}
