package com.outsourcing.domain.dashboard.repository;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.domain.dashboard.dto.DashboardDto;
import com.outsourcing.domain.dashboard.dto.SummaryMyTaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashboardRepository extends JpaRepository<Task, Long> {
    @Query("SELECT new com.outsourcing.domain.dashboard.dto.DashboardDto(t.assignee.id, t.status, t.dataStatus, t.dueDate) FROM Task t")
    List<DashboardDto> findAllTaskStatus();

    @Query("SELECT new com.outsourcing.domain.dashboard.dto.SummaryMyTaskDto(t.id, t.title, t.status, t.dataStatus, t.priority, t.dueDate) FROM Task t WHERE t.assignee.id = :userId")
    List<SummaryMyTaskDto> findAllMyTaskStatusByUserId(@Param("userId") Long id);
}
