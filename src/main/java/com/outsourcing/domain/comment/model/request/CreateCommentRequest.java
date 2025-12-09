package com.outsourcing.domain.comment.model.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String content;
    private Long parentId;

}
