package com.outsourcing.domain.task.repository;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskQueryRepository {

    Page<Task> search(
            Pageable pageable,
            TaskStatus status,
            String keyword,
            Long assigneeId
    );
}
