package com.outsourcing.domain.comment.dto.response;

import com.outsourcing.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryUserResponse {

    private final Long id;
    private final String username;
    private final String name;

    public static SummaryUserResponse from(User user) {

        return new SummaryUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }
}
