package com.outsourcing.domain.comment.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.outsourcing.common.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCommentResponse {

    private final Long id;

    private final Long taskId;

    private final Long userId;

    private final DetailUserResponse user;

    private final String content;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private final Long parentId;

    public static GetCommentResponse from(Comment comment) {

        return new GetCommentResponse(
                comment.getId(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                DetailUserResponse.from(comment.getUser()),
                comment.getContent()
        );
    }

}
