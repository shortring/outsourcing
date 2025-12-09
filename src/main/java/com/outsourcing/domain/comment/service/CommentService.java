package com.outsourcing.domain.comment.service;

import com.outsourcing.common.entity.Comment;
import com.outsourcing.common.entity.Task;
import com.outsourcing.common.entity.User;
import com.outsourcing.domain.comment.model.request.CreateCommentRequest;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.repository.CommentRepository;
import com.outsourcing.domain.task.TaskRepository;
import com.outsourcing.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
    }

    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task가 존재하지 않습니다."));
    }
}
