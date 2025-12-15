package com.outsourcing.domain.task.repository;

import com.outsourcing.common.enums.DataStatus;
import com.outsourcing.common.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndDataStatus(Long id, DataStatus status);

    @Query("""
           SELECT t
           FROM Task t
           WHERE t.title LIKE %:query% OR t.description LIKE %:query%
           """)
    List<Task> searchByKeyword(@Param("query") String query);
}
