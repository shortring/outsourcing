package com.outsourcing.domain.dashboard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatsDashboardResponse {
    private final Long totalTasks;
    private final Long completedTasks;
    private final Long inProgressTasks;
    private final Long todoTasks;
    private final Long overdueTasks;
    private final Long teamProgress;
    private final Long myTasksToday;
    private final Long completionRate;
}
