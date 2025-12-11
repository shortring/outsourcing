package com.outsourcing.domain.task.dto;

import com.outsourcing.common.entity.task.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateTaskRequest(
        @NotBlank String title,
        String description,
        TaskPriority priority,
        @NotNull Long assigneeId,
        Instant dueDate
) {
}
/* FE
* export interface CreateTaskRequest {
  title: string;
  description?: string;
  priority: TaskPriority;
  assigneeId: number;
  dueDate?: string;
}*/