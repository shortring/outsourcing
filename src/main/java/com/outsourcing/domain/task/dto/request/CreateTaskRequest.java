package com.outsourcing.domain.task.dto.request;

import com.outsourcing.common.entity.task.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

// 2025-12-13 : 작성자 : 탁진수
public record CreateTaskRequest(
        @NotBlank String title,
        String description,
        TaskPriority priority,
        @NotNull Long assigneeId,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate
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