package com.outsourcing.domain.comment.service;

import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.Comment;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.enums.IsDeleted;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.comment.model.dto.CommentDto;
import com.outsourcing.domain.comment.model.request.CreateCommentRequest;
import com.outsourcing.domain.comment.model.request.UpdateCommentRequest;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.GetCommentResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
import com.outsourcing.domain.comment.repository.CommentRepository;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse createComment(Long taskId, CreateCommentRequest request, CustomUserDetails userDetails) {
        User findUser = getUser(userDetails.getUserId());
        Task findTask = getTask(taskId);

        Comment parentComment = null;
        Long commentGroup = null;

        // 일반 댓글이 아닌 답글을 달겠다는 요청일 때
        if (request.getParentId() != null) {

            parentComment = getComment(request.getParentId());
            commentGroup = parentComment.getCommentGroup(); // 답글일 경우 commentGroup을 부모 댓글 commentGroup으로 설정

            // 답글을 달겠다는 요청일 때, 요청의 부모 댓글의 taskId와 pathVariable의 taskId가 같은지 검증
            if (!parentComment.getTask().getId().equals(taskId)) {
                throw new CustomException(ErrorMessage.BAD_REQUEST_PARENT_COMMENT_TASK_MISMATCH);
            }

            // 무한 댓글(답글에 답글...구조)을 막기 위해 parentId의 부모 댓글이 null인지 검증
            // null이 아니면 답글에 또 답글을 달려고 하는 것이므로 예외 처리
            if (parentComment.getParentComment() != null) {
                throw new CustomException(ErrorMessage.BAD_REQUEST_REPLY_TO_REPLY_NOT_ALLOWED);
            }
        }

        Comment comment = new Comment(
                findUser,
                findTask,
                parentComment,
                request.getContent(),
                commentGroup
        );

        Comment savedComment = commentRepository.save(comment);

        // 부모 댓글일 경우 댓글 생성 후 commentGroup를 자기 id로 설정
        if (parentComment == null) {
            savedComment.updateCommentGroup(comment.getId());
        }

        CommentDto commentDto = CommentDto.from(savedComment);

        return CreateCommentResponse.from(commentDto);
    }

    @Transactional(readOnly = true)
    public PagedResponse<GetCommentResponse> getComment(Long taskId, Integer page, Integer size, String sort) {

        checkTaskExists(taskId);

        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> comments;

        if (sort.equals("newest")) {
            comments = commentRepository.findCommentSortedByNewest(pageable, taskId, IsDeleted.FALSE);
        } else {
            comments = commentRepository.findCommentSortedByOldest(pageable, taskId, IsDeleted.FALSE);
        }

        Page<GetCommentResponse> commentResponsePage = comments.map(GetCommentResponse::from);
        return PagedResponse.from(commentResponsePage);
    }

    @Transactional
    public UpdateCommentResponse updateComment(Long taskId, Long commentId, UpdateCommentRequest request, CustomUserDetails userDetails) {

        checkTaskExists(taskId);

        Comment comment = getComment(commentId);

        // 수정하려는 댓글이 자신의(로그인 된 유저의) 댓글이 맞는지 검증
        if (!comment.getUser().getId().equals(userDetails.getUserId())) {
            throw new CustomException(ErrorMessage.FORBIDDEN_NO_PERMISSION_UPDATE_COMMENT);
        }

        comment.updateComment(request.getContent());

        commentRepository.flush();

        CommentDto commentDto = CommentDto.from(comment);

        return UpdateCommentResponse.from(commentDto);
    }

    @Transactional
    public void deleteComment(Long taskId, Long commentId, CustomUserDetails userDetails) {

        checkTaskExists(taskId);

        Comment comment = getComment(commentId);

        // 삭제하려는 댓글이 자신의(로그인 된 유저의) 댓글이 맞는지 검증
        if (!comment.getUser().getId().equals(userDetails.getUserId())) {
            throw new CustomException(ErrorMessage.FORBIDDEN_NO_PERMISSION_REMOVE_COMMENT);
        }

        // 부모 댓글이면 같은 그룹의 댓글 전부 논리 삭제, 답글이면 해당 답글만 논리 삭제
        if (comment.getParentComment() == null) {
            commentRepository.softDeleteWithParentComment(comment.getCommentGroup(), IsDeleted.TRUE);
        } else {
            commentRepository.softDeleteWithChildComment(comment.getId(), IsDeleted.TRUE);
        }
    }

    // 요청이 들어온 Task가 존재하는지 검증
    private void checkTaskExists(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new CustomException(ErrorMessage.NOT_FOUND_TASK);
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }

    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT));
    }
}
