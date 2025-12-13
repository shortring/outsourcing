package com.outsourcing.domain.task.dto.response;

import com.outsourcing.common.entity.User;


public record AssigneeResponse(
        Long id,
        String username,
        String name,
        String email
) {
    public static AssigneeResponse from(User assignee) {
        return new AssigneeResponse(
                assignee.getId(),
                assignee.getUsername(),
                assignee.getName(),
                assignee.getEmail()
        );
    }
}
