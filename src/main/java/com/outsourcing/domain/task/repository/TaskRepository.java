package com.outsourcing.domain.task.repository;

import com.outsourcing.common.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
