package com.outsourcing.domain.dashboard.dto;

import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.DataStatus;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class DashboardDto {
    private final Long assigneeId;
    private final TaskStatus status;
    private final DataStatus isActivity;
    private final LocalDate dueDate;

    public DashboardDto(Long assigneeId, TaskStatus status, DataStatus isActivity, Instant dueDate) {
        this.assigneeId = assigneeId;
        this.status = status;
        this.isActivity = isActivity;
        this.dueDate = LocalDateTime.ofInstant(dueDate, ZoneId.systemDefault()).toLocalDate();
    }
}
