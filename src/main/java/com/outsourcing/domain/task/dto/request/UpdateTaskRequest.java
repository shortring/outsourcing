package com.outsourcing.domain.task.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTaskRequest(
        @NotBlank String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        @NotNull Long assigneeId,
        @NotNull LocalDateTime dueDate
) {
}
