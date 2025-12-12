package com.outsourcing.domain.task.repository;

import com.outsourcing.common.enums.DataStatus;
import com.outsourcing.common.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndDataStatus(Long id, DataStatus status);
}
