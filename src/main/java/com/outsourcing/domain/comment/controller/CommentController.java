package com.outsourcing.domain.comment.controller;

import com.outsourcing.domain.comment.model.request.CreateCommentRequest;
import com.outsourcing.domain.comment.model.request.UpdateCommentRequest;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.GetCommentResponse;
import com.outsourcing.domain.comment.model.response.PageResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
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

    @GetMapping
    public ResponseEntity<PageResponse<GetCommentResponse>> readComment(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page, // 현재 페이지
            @RequestParam(defaultValue = "10") int size) { // 크기

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.readComment(taskId, page, size));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.updateComment(taskId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId) {

        commentService.deleteComment(taskId, commentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
