package com.outsourcing.domain.task.repository;

import com.outsourcing.common.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
           SELECT t
           FROM Task t
           WHERE t.title LIKE %:query% OR t.description LIKE %:query%
           """)
    List<Task> searchByKeyword(@Param("query") String query);
}
