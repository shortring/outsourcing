package com.outsourcing.domain.task.dto.response;

import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.domain.task.dto.TaskDetailDto;

import java.time.Instant;

public record TaskDetailResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long assigneeId,
        AssigneeDetailResponse assignee, // id, username
        Instant createdAt,
        Instant updatedAt,
        Instant dueDate
){

    public static TaskDetailResponse of(TaskDetailDto taskDto) {
        return new TaskDetailResponse(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.status(),
                taskDto.priority(),
                taskDto.assigneeId(),
                AssigneeDetailResponse.from(
                        taskDto
                ),
                taskDto.createdAt(),
                taskDto.updatedAt(),
                taskDto.dueDate()
        );
    }
}