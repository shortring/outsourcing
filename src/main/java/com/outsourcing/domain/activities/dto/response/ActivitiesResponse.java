package com.outsourcing.domain.activities.dto.response;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.domain.activities.dto.ActivityType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ActivitiesResponse {

    Long id;
    ActivityType type;
    Long userId;
    ActivityUserResponse user;
    Long taskId;
    Instant timestamp;
    String description;

    private ActivitiesResponse(Long id, ActivityType type, Long userId, User user, Long taskId, Instant timestamp, String description) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.user = ActivityUserResponse.of(user);
        this.taskId = taskId;
        this.timestamp = timestamp;
        this.description = description;
    }

    public static ActivitiesResponse of(Long id, ActivityType type, Long userId, User user, Long taskId, Instant timestamp, String description) {
        return new ActivitiesResponse(
                id,
                type,
                userId,
                user,
                taskId,
                timestamp,
                description
        );
    }

    public static ActivitiesResponse from(Activity activity) {
        return Activity.from(activity);
    }
}