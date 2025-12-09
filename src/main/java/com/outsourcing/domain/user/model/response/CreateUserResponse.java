package com.outsourcing.domain.user.model.response;

import com.outsourcing.domain.user.model.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class CreateUserResponse {
    private final String username;
    private final String email;
    private final String name;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static CreateUserResponse from(UserDto dto) {
        return new CreateUserResponse(
                dto.getUsername(),
                dto.getEmail(),
                dto.getName(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
