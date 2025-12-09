package com.outsourcing.domain.comment.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PageResponse<T> {

    private final List<T> content;
    private final Long totalElements;
    private final int totalPages;
    private final int size;
    private final int number;

    public static <T> PageResponse<T> from(Page<T> page) {

        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber()
        );
    }
}
