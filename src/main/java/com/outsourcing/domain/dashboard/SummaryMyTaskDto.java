package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class SummaryMyTaskDto {
    private final Long id;
    private final String title;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final Instant dueDate;
}
