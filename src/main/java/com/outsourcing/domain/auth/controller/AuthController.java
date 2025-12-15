package com.outsourcing.domain.auth.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.auth.model.request.LoginRequest;
import com.outsourcing.domain.auth.model.request.VerifyPasswordRequest;
import com.outsourcing.domain.auth.model.response.LoginResponse;
import com.outsourcing.domain.auth.model.response.VerifyPasswordResponse;
import com.outsourcing.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //로그인
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginApi(@RequestBody LoginRequest request) {

        LoginResponse result = new LoginResponse(authService.login(request));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("로그인 성공", result));
    }

    //비밀번호 검증
    @PostMapping("/users/verify-password")
    public ResponseEntity<ApiResponse<VerifyPasswordResponse>> verifyPasswordApi(
            @RequestBody VerifyPasswordRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        VerifyPasswordResponse result = authService.verifyPassword(request, userDetails);

        if (!result.isValid()) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("비밀번호가 올바르지 않습니다.", result));
        }

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("비밀번호가 확인되었습니다.", result));
    }

}
