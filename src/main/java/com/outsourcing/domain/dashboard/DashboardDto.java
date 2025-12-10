package com.outsourcing.domain.dashboard;

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

    public DashboardDto(Long assigneeId, TaskStatus status, Instant dueDate) {
        this.assigneeId = assigneeId;
        this.status = status;
        this.dueDate = LocalDateTime.ofInstant(dueDate, ZoneId.systemDefault()).toLocalDate();
    }
}
