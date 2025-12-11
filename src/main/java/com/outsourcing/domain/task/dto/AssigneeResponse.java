package com.outsourcing.domain.task.dto;

import com.outsourcing.common.entity.User;

// FE Mock User 필드. : id username, email, name, role, createdAt
public record AssigneeResponse(
        Long id,
        String username,
        String name,
        String email
) {
    public static AssigneeResponse of(User assignee) {
        return new AssigneeResponse(
                assignee.getId(),
                assignee.getUsername(),
                assignee.getName(),
                assignee.getEmail()
        );
    }
}
