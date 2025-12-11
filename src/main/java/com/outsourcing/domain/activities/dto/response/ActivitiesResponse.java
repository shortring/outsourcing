package com.outsourcing.domain.activities.dto.response;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
public class ActivitiesResponse {
    Long id;
    String type;
    Long userId;
    User user;
    Long taskId;
    Instant timestamp;
    String description;

    private ActivitiesResponse(Long id, String type, Long userId, User user, Long taskId, Instant timestamp, String description) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.user = user;
        this.taskId = taskId;
        this.timestamp = timestamp;
        this.description = description;
    }

    public static ActivitiesResponse of(Long id, String type, Long userId, User user, Long taskId, Instant timestamp, String description) {
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