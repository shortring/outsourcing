package com.outsourcing.domain.user.model.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    String name;
    String email;
    String password;
}
