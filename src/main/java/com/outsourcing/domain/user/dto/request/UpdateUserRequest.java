package com.outsourcing.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Size(min = 2, max = 50, message = "2~50자로 입력해야 합니다.")
    @NotBlank(message = "이름을 입력해주세요")
    String name;

    @Email
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "이메일 형식이 올바르지 않습니다."
    )
    @NotBlank(message = "email을 입력해주세요")
    String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&])([a-zA-Z0-9@$!%*#?&]{8,})$",
            message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자(@$!%*#?&)를 최소 1글자씩 포함하여 8자이상 입력해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요")
    String password;
}
