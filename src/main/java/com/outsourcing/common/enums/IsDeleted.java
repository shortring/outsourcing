package com.outsourcing.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IsDeleted {

    TRUE("1", "삭제된 데이터"),
    FALSE("0", "삭제되지 않은 데이터");

    private final String isDeleted;
    private final String description;
}
