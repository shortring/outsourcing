package com.outsourcing.domain.task.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

// 2025-12-13 : 작성자 : 탁진수
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateTaskRequest(
        @NotBlank String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        @NotNull Long assigneeId,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate

) {
}