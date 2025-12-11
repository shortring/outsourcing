package com.outsourcing.domain.task.dto;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;

import java.time.Instant;

// 값 : id, 타이틀, 세부사항, 스테이터스, 우선순위, 담당자, 베이스 엔티티 2종, DueDate.
public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long assigneeId,
        AssigneeResponse assignee, // id, username
        Instant createdAt,
        Instant updatedAt,
        Instant dueDate
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getAssigneeId(),
                AssigneeResponse.of(
                        task.getAssignee()
                ),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }
}
