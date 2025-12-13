package com.outsourcing.common.enums;

import com.outsourcing.domain.activities.dto.ActivityType;

import java.time.Instant;

public record ActivityDraft(
        ActivityType type,
        Long taskId,
        Long userId,
        String description,
        Instant timestamp
) {
    public ActivityDraft withTaskId(Long taskId) {
        return new ActivityDraft(type, taskId, userId, description, timestamp);
    }
    public ActivityDraft withDescription(String description){
        return new ActivityDraft(type, taskId, userId, description, timestamp);
    }
}
