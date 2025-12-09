package com.outsourcing.domain.comment.model.response;

import com.outsourcing.common.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCommentResponse {

    private final Long id;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final DetailUserResponse user;

    public static GetCommentResponse from(Comment comment) {

        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                DetailUserResponse.from(comment.getUser())
        );
    }

}
