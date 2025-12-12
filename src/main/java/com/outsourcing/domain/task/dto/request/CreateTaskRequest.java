package com.outsourcing.domain.task.dto.request;

import com.outsourcing.common.entity.task.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotBlank String title,
        String description,
        TaskPriority priority,
        @NotNull Long assigneeId,
        @NotNull LocalDateTime dueDate
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