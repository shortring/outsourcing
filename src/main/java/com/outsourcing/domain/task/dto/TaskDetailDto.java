package com.outsourcing.domain.task.dto;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.domain.task.dto.response.AssigneeDetailResponse;

import java.time.Instant;

public record TaskDetailDto(

        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long assigneeId,
        AssigneeDetailResponse assignee,
        Instant createdAt,
        Instant updatedAt,
        Instant dueDate
) {
    public static TaskDetailDto from(Task task) {
        return new TaskDetailDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getAssigneeId(),
                AssigneeDetailResponse.of(
                        task.getAssignee()
                ),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }
}
