package com.outsourcing.domain.comment.repository;

import com.outsourcing.common.entity.Comment;
import com.outsourcing.common.enums.IsDeleted;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 최신순 정렬
    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.task.id = :taskId AND c.isDeleted = :isDeleted
            ORDER BY c.commentGroup DESC,
            CASE WHEN c.parentComment IS NULL THEN 0 ELSE 1 END ASC,
            c.createdAt DESC
            """)
    Page<Comment> findCommentSortedByNewest(Pageable pageable, Long taskId, IsDeleted isDeleted);

    // 오래된순 정렬
    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.task.id = :taskId AND c.isDeleted = :isDeleted
            ORDER BY c.commentGroup ASC,
            CASE WHEN c.parentComment IS NULL THEN 0 ELSE 1 END ASC,
            c.createdAt ASC
            """)
    Page<Comment> findCommentSortedByOldest(Pageable pageable, Long taskId, IsDeleted isDeleted);

    @Modifying
    @Query("""
            UPDATE Comment
            SET isDeleted = :isDeleted
            WHERE commentGroup = :commentGroup
            """)
    void softDeleteWithParentComment(Long commentGroup, IsDeleted isDeleted);

    @Modifying
    @Query("""
            UPDATE Comment
            SET isDeleted = :isDeleted
            WHERE id = :id
            """)
    void softDeleteWithChildComment(Long id, IsDeleted isDeleted);
}
