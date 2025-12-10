package com.outsourcing.domain.comment.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.comment.model.request.CreateCommentRequest;
import com.outsourcing.domain.comment.model.request.UpdateCommentRequest;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.GetCommentResponse;
import com.outsourcing.domain.comment.model.response.PageResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
import com.outsourcing.domain.comment.service.CommentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<CreateCommentResponse>> createComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("댓글이 작성되었습니다.", commentService.createComment(taskId, request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<GetCommentResponse>>> getComment(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "newest") String sort) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("댓글 목록을 조회했습니다.", commentService.getComment(taskId, page, size, sort)));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> updateComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("댓글이 수정되었습니다.", commentService.updateComment(taskId, commentId, request)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId) {

        commentService.deleteComment(taskId, commentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("댓글이 삭제되었습니다.", null));
    }
}
