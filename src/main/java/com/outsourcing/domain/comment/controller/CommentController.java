package com.outsourcing.domain.comment.controller;

import com.outsourcing.domain.comment.model.request.CreateCommentRequest;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long taskId,
            @RequestBody CreateCommentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(taskId, request));
    }
}
