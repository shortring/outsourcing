package com.outsourcing.domain.activities.dto.response;

import com.outsourcing.common.entity.User;
import lombok.Getter;

@Getter
public class ActivityUserResponse {

    Long id;
    String username;
    String name;

    private ActivityUserResponse(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public static ActivityUserResponse of(User user) {
        return new ActivityUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }
}
