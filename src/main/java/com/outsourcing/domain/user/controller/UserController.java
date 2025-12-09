package com.outsourcing.domain.user.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.request.LoginRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.model.response.LoginResponse;
import com.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<CreateUserResponse>> handlerCreateUser(@Valid @RequestBody CreateUserRequest request) {

        CreateUserResponse result = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 성공",result));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = userService.login(request);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
