package com.outsourcing.domain.user.dto.response;

import com.outsourcing.common.enums.UserRole;
import com.outsourcing.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class AvailableUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final UserRole role;
    private final Instant createdAt;

    public static AvailableUserResponse from(UserDto dto) {
        return new AvailableUserResponse(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getName(),
                dto.getRole(),
                dto.getCreatedAt()
        );

    }
}
