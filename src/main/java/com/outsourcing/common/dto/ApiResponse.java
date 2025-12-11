package com.outsourcing.common.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final Instant timestamp;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    // 성공 시 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 에러 시 응답
    public static ApiResponse<?> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}