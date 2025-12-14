package com.outsourcing.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VerifyPasswordRequest {

    @NotBlank(message = "password를 입력해주세요")
    String password;
}
