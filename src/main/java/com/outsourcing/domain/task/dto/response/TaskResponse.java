package com.outsourcing.domain.task.dto.response;

import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.domain.task.dto.TaskDto;

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
    public static TaskResponse from(TaskDto taskDto) {
        return new TaskResponse(
                taskDto.id(),
                taskDto.title(),
                taskDto.description(),
                taskDto.status(),
                taskDto.priority(),
                taskDto.assigneeId(),
                AssigneeResponse.from(
                        taskDto
                ),
                taskDto.createdAt(),
                taskDto.updatedAt(),
                taskDto.dueDate()
        );
    }

}
