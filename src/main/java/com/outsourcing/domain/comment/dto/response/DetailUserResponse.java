package com.outsourcing.domain.comment.dto.response;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DetailUserResponse {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final UserRole role;

    public static DetailUserResponse from(User user) {

        return new DetailUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
