package com.outsourcing.domain.user.dto.request;

import com.outsourcing.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @Pattern(regexp = "^([a-zA-Z0-9]{4,20})$",
            message = "아이디는 4~20자의 영문 또는 숫자만 가능합니다.")
    @NotBlank(message = "아이디를 입력해주세요")
    private String username;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "email을 입력해주세요")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&])([a-zA-Z0-9@$!%*#?&]{8,})$",
            message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자(@$!%*#?&)를 최소 1글자씩 포함하여 8자이상 입력해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Size(min = 2, max = 50, message = "2~50자로 입력해야 합니다.")
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    private final UserRole role = UserRole.USER;

}
