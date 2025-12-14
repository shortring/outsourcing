package com.outsourcing.domain.task.dto.response;

import com.outsourcing.common.entity.User;
import com.outsourcing.domain.task.dto.TaskDetailDto;


public record AssigneeDetailResponse(

        Long id,
        String username,
        String name,
        String email
) {
    public static AssigneeDetailResponse of(User assignee) {
        return new AssigneeDetailResponse(
                assignee.getId(),
                assignee.getUsername(),
                assignee.getName(),
                assignee.getEmail()
        );
    }

    public static AssigneeDetailResponse from(TaskDetailDto task) {
        return new AssigneeDetailResponse(
                task.assignee().id(),
                task.assignee().username(),
                task.assignee().name(),
                task.assignee().email()
        );
    }

}
