package com.outsourcing.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IsDeleted {
    TRUE("1", "탈퇴한 사용자"),
    FALSE("0", "회원인 사용자")
    ;
    private  final String isDeleted;
    private  final String description;
}
