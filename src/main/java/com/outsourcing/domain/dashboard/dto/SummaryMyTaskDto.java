package com.outsourcing.domain.dashboard.dto;

import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.DataStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class SummaryMyTaskDto {
    private final Long id;
    private final String title;
    private final TaskStatus status;
    private final DataStatus isActivity;
    private final TaskPriority priority;
    private final Instant dueDate;
}
