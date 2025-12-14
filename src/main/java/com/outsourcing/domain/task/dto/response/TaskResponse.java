package com.outsourcing.domain.task.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.domain.task.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class TaskResponse {

    Long id;
    String title;
    String description;
    TaskStatus status;
    TaskPriority priority;
    Long assigneeId;
    AssigneeResponse assignee;
    Instant createdAt;
    Instant updatedAt;
    Instant dueDate;

    public static TaskResponse from(TaskDto taskDto) {
        return new TaskResponse(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStatus(),
                taskDto.getPriority(),
                taskDto.getAssigneeId(),
                AssigneeResponse.from(
                        taskDto
                ),
                taskDto.getCreatedAt(),
                taskDto.getUpdatedAt(),
                taskDto.getDueDate()
        );
    }

}
