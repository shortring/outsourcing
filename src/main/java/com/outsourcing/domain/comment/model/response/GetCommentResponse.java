package com.outsourcing.domain.comment.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.outsourcing.common.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class GetCommentResponse {

    private final Long id;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final DetailUserResponse user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long parentId;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static GetCommentResponse from(Comment comment) {

        Long parentId = (comment.getParentComment() == null) ? null : comment.getParentComment().getId();

        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                DetailUserResponse.from(comment.getUser()),
                parentId,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
