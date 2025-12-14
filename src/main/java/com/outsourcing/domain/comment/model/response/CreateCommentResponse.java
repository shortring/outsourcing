package com.outsourcing.domain.comment.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.outsourcing.domain.comment.model.dto.CommentDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final SummaryUserResponse user;
    private final String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long parentId;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static CreateCommentResponse from(CommentDto comment) {

        Long parentId = (comment.getParentComment() == null) ? null : comment.getParentComment().getId();

        return new CreateCommentResponse(
                comment.getId(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                SummaryUserResponse.from(comment.getUser()),
                comment.getContent(),
                parentId,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
