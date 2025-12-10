package com.outsourcing.domain.user.model;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class UserDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final UserRole role;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
