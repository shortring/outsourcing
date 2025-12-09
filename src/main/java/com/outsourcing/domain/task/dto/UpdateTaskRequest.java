package com.outsourcing.domain.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTaskRequest(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long assigneeId,
        Instant dueDate
) {
}
