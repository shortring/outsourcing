package com.outsourcing.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "username과 password는 필수입니다.")
    private String username;

    @NotBlank(message = "username과 password는 필수입니다.")
    private String password;

}
