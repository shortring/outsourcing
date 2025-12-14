package com.outsourcing.domain.task.dto.request;

import com.outsourcing.common.entity.task.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Getter
@RequiredArgsConstructor
public class CreateTaskRequest {

    @NotBlank
    String title;

    String description;

    TaskPriority priority;

    @NotNull
    Long assigneeId;

    @NotNull
    LocalDateTime dueDate;
}
