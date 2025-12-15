package com.outsourcing.domain.comment.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.comment.dto.request.CreateCommentRequest;
import com.outsourcing.domain.comment.dto.request.UpdateCommentRequest;
import com.outsourcing.domain.comment.dto.response.CreateCommentResponse;
import com.outsourcing.domain.comment.dto.response.GetCommentResponse;
import com.outsourcing.domain.comment.dto.response.UpdateCommentResponse;
import com.outsourcing.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping
    public ResponseEntity<ApiResponse<CreateCommentResponse>> createCommentApi(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("댓글이 작성되었습니다.", commentService.createComment(taskId, request, userDetails)));
    }

    //댓글 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<GetCommentResponse>>> getCommentApi(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "newest") String sort) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("댓글 목록을 조회했습니다.", commentService.getComment(taskId, page, size, sort)));
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> updateCommentApi(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("댓글이 수정되었습니다.", commentService.updateComment(taskId, commentId, request, userDetails)));
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteCommentApi(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.deleteComment(taskId, commentId, userDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("댓글이 삭제되었습니다.", null));
    }
}
