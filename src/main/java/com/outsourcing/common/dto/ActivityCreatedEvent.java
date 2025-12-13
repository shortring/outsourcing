package com.outsourcing.common.dto;

import com.outsourcing.common.enums.ActivityDraft;
import com.outsourcing.domain.activities.dto.ActivityType;

import java.time.Instant;

public record ActivityCreatedEvent(
        ActivityType type,
        Long taskId,
        Long userId,
        String description,
        Instant timestamp
) {
    public static ActivityCreatedEvent of(ActivityDraft draft) {
        return new ActivityCreatedEvent(
                draft.type(),
                draft.taskId(),
                draft.userId(),
                draft.description(),
                draft.timestamp()
        );
    }
}
