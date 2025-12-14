package com.outsourcing.domain.user.dto.response;

import com.outsourcing.common.enums.UserRole;
import com.outsourcing.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class CreateUserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final UserRole role;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static CreateUserResponse from(UserDto dto) {
        return new CreateUserResponse(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getName(),
                dto.getRole(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
