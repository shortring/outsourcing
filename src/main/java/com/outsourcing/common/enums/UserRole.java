package com.outsourcing.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER", "사용자 권한"),
    ADMIN("ROLE_ADMIN", "관리자 권한");

    private final String role; //Spring Security에서 사용하는 권한 이름 설정
    private final String description; //권한 추가 설명
}
