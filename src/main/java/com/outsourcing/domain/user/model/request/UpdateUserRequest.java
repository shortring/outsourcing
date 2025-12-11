package com.outsourcing.domain.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotBlank(message = "이름을 입력해주세요")
    String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "email을 입력해주세요")
    String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    String password;
}
