package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempTaskRepository extends JpaRepository<Task, Long> {
}
