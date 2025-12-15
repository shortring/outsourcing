package com.outsourcing.domain.dashboard.dto;

import com.outsourcing.common.entity.task.TaskStatus;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class DashboardDto {

    private final Long assigneeId;
    private final TaskStatus status;
    private final LocalDate dueDate;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public DashboardDto(Long assigneeId, TaskStatus status, Instant dueDate, Instant createdAt, Instant updatedAt) {
        this.assigneeId = assigneeId;
        this.status = status;
        this.dueDate = LocalDateTime.ofInstant(dueDate, ZoneId.systemDefault()).toLocalDate();
        this.createdAt = LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault()).toLocalDate();
        this.updatedAt = LocalDateTime.ofInstant(updatedAt, ZoneId.systemDefault()).toLocalDate();
    }
}
