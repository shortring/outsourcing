package com.outsourcing.domain.comment.model.response;

import com.outsourcing.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 댓글 생성 응답 시 요약된 유저 정보를 제공하는 DTO
 *
 */
@Getter
@RequiredArgsConstructor
public class SummaryUserResponse {

    private final Long id;
    private final String username;
    private final String name;

    public static SummaryUserResponse from(User user) {

        return new SummaryUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }
}
