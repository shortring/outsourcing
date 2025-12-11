package com.outsourcing.domain.search.model.response;

import com.outsourcing.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchUserResponse {

    private final long id;
    private final String name;
    private final String username;

    public static SearchUserResponse from(User user) {

        return new SearchUserResponse(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
    }
}

