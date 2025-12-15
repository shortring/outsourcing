package com.outsourcing.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyPasswordResponse {

    private boolean valid;
}
