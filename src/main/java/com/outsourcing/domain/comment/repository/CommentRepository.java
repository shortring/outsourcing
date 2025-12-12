package com.outsourcing.domain.comment.repository;

import com.outsourcing.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 최신순 정렬
    @Query("""
           SELECT c
           FROM Comment c
           WHERE c.task.id = :taskId
           ORDER BY c.commentGroup DESC,
           CASE WHEN c.parentComment IS NULL THEN 0 ELSE 1 END ASC,
           c.createdAt DESC
           """)
    Page<Comment> findCommentSortedByNewest(Pageable pageable, Long taskId);

    // 오래된순 정렬
    @Query("""
           SELECT c
           FROM Comment c
           WHERE c.task.id = :taskId
           ORDER BY c.commentGroup ASC,
           CASE WHEN c.parentComment IS NULL THEN 0 ELSE 1 END ASC,
           c.createdAt ASC
           """)
    Page<Comment> findCommentSortedByOldest(Pageable pageable, Long taskId);
}
