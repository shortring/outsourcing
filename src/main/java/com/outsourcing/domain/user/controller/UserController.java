package com.outsourcing.domain.user.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.user.dto.request.CreateUserRequest;
import com.outsourcing.domain.user.dto.request.UpdateUserRequest;
import com.outsourcing.domain.user.dto.response.AvailableUserResponse;
import com.outsourcing.domain.user.dto.response.CreateUserResponse;
import com.outsourcing.domain.user.dto.response.GetUserResponse;
import com.outsourcing.domain.user.dto.response.UpdateUserResponse;
import com.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUserApi(@Valid @RequestBody CreateUserRequest request) {

        CreateUserResponse result = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입이 완료되었습니다.", result));
    }

    // 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<GetUserResponse>> getUserApi(
            @PathVariable Long userId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        GetUserResponse result = userService.getUser(userId, userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("사용자 정보 조회 성공", result));
    }

    // 사용자 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetUserResponse>>> getUsersApi() {

        List<GetUserResponse> result = userService.getAllUser();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("사용자 목록 조회 성공", result));
    }

    // 사용자 수정
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUserApi(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UpdateUserResponse result = userService.updateUser(userId, request, userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("사용자 목록 조회 성공", result));
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserApi(
            @PathVariable Long userId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        userService.deleteUser(userId, userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("회원 탈퇴가 완료되었습니다.", null));
    }

    // 추가 가능한 사용자 목록 조회
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<AvailableUserResponse>>> getAvailableUsers(@RequestParam(required = false) Long teamId) {

        List<AvailableUserResponse> result = userService.getAvailableUsers(teamId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("추가 가능한 사용자 목록 조회 성공", result));
    }


}
