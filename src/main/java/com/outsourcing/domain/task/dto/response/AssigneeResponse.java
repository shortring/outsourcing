package com.outsourcing.domain.task.dto.response;

import com.outsourcing.common.entity.User;
import com.outsourcing.domain.task.dto.TaskDto;

public record AssigneeResponse(
        Long id,
        String username,
        String name
) {
    public static AssigneeResponse of(User assignee) {
        return new AssigneeResponse(
                assignee.getId(),
                assignee.getUsername(),
                assignee.getName()
        );
    }

    public static AssigneeResponse from(TaskDto task) {
        return new AssigneeResponse(
                task.assignee().id(),
                task.assignee().username(),
                task.assignee().name()
        );
    }
}
