package com.outsourcing.domain.user.model.response;

import com.outsourcing.common.enums.UserRole;
import com.outsourcing.domain.user.model.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class UpdateUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final UserRole role;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static UpdateUserResponse from(UserDto dto) {
        return new UpdateUserResponse(
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
