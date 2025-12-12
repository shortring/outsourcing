package com.outsourcing.domain.task.dto.request;

import com.outsourcing.common.entity.task.TaskStatus;

public record UpdateTaskStatusRequest(TaskStatus status) {
}
