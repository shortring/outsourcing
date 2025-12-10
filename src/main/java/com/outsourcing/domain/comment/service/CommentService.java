package com.outsourcing.domain.comment.service;

import com.outsourcing.common.entity.Comment;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.comment.model.dto.CommentDto;
import com.outsourcing.domain.comment.model.request.CreateCommentRequest;
import com.outsourcing.domain.comment.model.request.UpdateCommentRequest;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.GetCommentResponse;
import com.outsourcing.domain.comment.model.response.PageResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
import com.outsourcing.domain.comment.repository.CommentRepository;
import com.outsourcing.domain.task.TaskRepository;
import com.outsourcing.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse createComment(Long taskId, CreateCommentRequest request) {
        User findUser = getUserOrThrow(1L); // 임시
        Task findTask = getTaskOrThrow(taskId);

        Comment parentComment = null;

        // 일반 댓글이 아닌 답글을 달겠다는 요청일 때
        if (request.getParentId() != null) {
            parentComment = getCommentOrThrow(request.getParentId());
            
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
                request.getContent()
        );

        CommentDto commentDto = CommentDto.from(commentRepository.save(comment));

        return CreateCommentResponse.from(commentDto);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetCommentResponse> getComment(Long taskId, Integer page, Integer size, String sort) {
        getTaskOrThrow(taskId);

        // 같은 Task에 달린 댓글인지 검증해야함
        
        Pageable pageable = null;

        if (sort.equals("newest")) {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        }

        Page<Comment> comments = commentRepository.findAllByTaskId(pageable, taskId);

        Page<GetCommentResponse> commentResponsePage = comments.map(GetCommentResponse::from);

        return PageResponse.from(commentResponsePage);
    }

    @Transactional
    public UpdateCommentResponse updateComment(Long taskId, Long commentId, UpdateCommentRequest request) {
        getTaskOrThrow(taskId);
        
        // 인증된 사용자인지 검증
        // 다른 사용자의 댓글인지 검증

        Comment comment = getCommentOrThrow(commentId);

        comment.updateComment(request.getContent());

        commentRepository.flush();

        CommentDto commentDto = CommentDto.from(comment);

        return UpdateCommentResponse.from(commentDto);
    }

    @Transactional
    public void deleteComment(Long taskId, Long commentId) {
        getTaskOrThrow(taskId);

        // 인증된 사용자인지 검증
        // 다른 사용자의 댓글인지 검증

        Comment comment = getCommentOrThrow(commentId);

        commentRepository.delete(comment);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));
    }

    private Comment getCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT));
    }
}
