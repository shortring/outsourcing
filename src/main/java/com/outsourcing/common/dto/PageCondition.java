package com.outsourcing.common.dto;

public record PageCondition(
        int page,
        int size
) {

    // 0based
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_MAX_SIZE = 50;

    public static PageCondition of(Integer rawPage, Integer rawSize) {
        int page=(rawPage==null)
                ? DEFAULT_PAGE
                : Math.max(rawPage, DEFAULT_PAGE);

        int size=(rawSize==null||rawSize<=0)
                ? DEFAULT_SIZE
                : Math.min(rawSize, DEFAULT_MAX_SIZE); // 요구사항 밖.

        return new PageCondition(page, size);
    }
}
