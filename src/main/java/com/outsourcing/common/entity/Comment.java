package com.outsourcing.common.entity;

import com.outsourcing.common.entity.task.Task;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment parentComment;

    private String content;

    @Column(name = "comment_group")
    private Long commentGroup;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public Comment(User user, Task task, Comment parentComment, String content, Long commentGroup) {
        this.user = user;
        this.task = task;
        this.parentComment = parentComment;
        this.content = content;
        this.commentGroup = commentGroup;
    }

    public void updateComment(String newContent) {
        this.content = (newContent == null) ? this.content : newContent;
    }

    public void updateCommentGroup(Long commentGroup) {
        this.commentGroup = commentGroup;
    }
}
