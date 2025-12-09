package com.outsourcing.domain.user.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.request.LoginRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.model.response.GetUserResponse;
import com.outsourcing.domain.user.model.response.LoginResponse;
import com.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<CreateUserResponse>> handlerCreateUser(@Valid @RequestBody CreateUserRequest request) {

        CreateUserResponse result = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입이 완료되었습니다.", result));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<GetUserResponse>> handlerGetUser(@PathVariable Long userId) {

        GetUserResponse result = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("사용자 정보 조회 성공", result));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<GetUserResponse>>> handlerGetUsers(@PathVariable Long userId) {

        List<GetUserResponse> result = userService.getAllUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("사용자 목록 조회 성공", result));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = userService.login(request);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
