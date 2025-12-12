package com.outsourcing.domain.comment.model.dto;

import com.outsourcing.common.entity.Comment;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class CommentDto {

    private final Long id;
    private final User user;
    private final Task task;
    private final Comment parentComment;
    private final String content;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static CommentDto from(Comment comment) {

        return new CommentDto(
                comment.getId(),
                comment.getUser(),
                comment.getTask(),
                comment.getParentComment(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
