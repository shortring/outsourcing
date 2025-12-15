package com.outsourcing.domain.activities.dto.response;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.domain.activities.dto.ActivityType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ActivitiesAllResponse {

    Long id;
    Long userId;
    ActivityUserResponse user;
    String action;
    ActivityType targetType;
    Long targetId;
    String description;
    Instant createdAt;

    private ActivitiesAllResponse(Long id, Long userId, User user, ActivityType type, Long taskId, Instant createdAt, String description) {
        this.id = id;
        this.userId = userId;
        this.user = ActivityUserResponse.of(user);
        this.action = type.getTypeName();
        this.targetType = type;
        this.targetId = taskId;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static ActivitiesAllResponse of(Long id, Long userId, User user, ActivityType type, Long taskId, Instant createdAt, String description) {
        return new ActivitiesAllResponse(
                id,
                userId,
                user,
                type,
                taskId,
                createdAt,
                description
        );
    }

    public static ActivitiesAllResponse from(Activity activity) {
        return Activity.fromAll(activity);
    }
}