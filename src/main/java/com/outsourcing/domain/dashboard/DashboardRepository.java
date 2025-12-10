package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardRepository extends JpaRepository<Task, Long> {
    @Query("SELECT new com.outsourcing.domain.dashboard.DashboardDto(t.assignee.id, t.status, t.dueDate) FROM Task t")
    List<DashboardDto> findTaskStatus();
}
