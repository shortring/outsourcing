package com.outsourcing.domain.comment.service;

import com.outsourcing.common.entity.Comment;
import com.outsourcing.common.entity.Task;
import com.outsourcing.common.entity.User;
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
        User findUser = getUser(1L); // 임시
        Task findTask = getTask(taskId);

        Comment parentComment = null;

        // 일반 댓글이 아닌 답글일 때
        if (request.getParentId() != null) {
            parentComment = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        }

        Comment comment = new Comment(
                findUser,
                findTask,
                parentComment,
                request.getContent()
        );

        commentRepository.save(comment);

        return CreateCommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetCommentResponse> readComment(Long taskId, int page, int size) {
        getTask(taskId);

        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> comments = commentRepository.findAllByTaskId(pageable, taskId);

        Page<GetCommentResponse> commentResponsePage = comments.map(GetCommentResponse::from);

        return PageResponse.from(commentResponsePage);
    }

    @Transactional
    public UpdateCommentResponse updateComment(Long taskId, Long commentId, UpdateCommentRequest request) {
        getTask(taskId);
        
        // 유저 검증 로직 들어가야 함

        Comment comment = getComment(commentId);

        comment.updateComment(request.getContent());
        commentRepository.flush();

        return UpdateCommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long taskId, Long commentId) {
        getTask(taskId);

        Comment comment = getComment(commentId);

        commentRepository.delete(comment);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
    }

    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task가 존재하지 않습니다."));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment가 존재하지 않습니다."));
    }
}
