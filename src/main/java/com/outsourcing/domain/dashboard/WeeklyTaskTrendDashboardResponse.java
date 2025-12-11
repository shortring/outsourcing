package com.outsourcing.domain.dashboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class WeeklyTaskTrendDashboardResponse {
    private final String name;
    private final Long tasks;
    private final Long completed;
    private final LocalDate date;

    public WeeklyTaskTrendDashboardResponse(WeeklyTaskDashboardDto dto) {
        this.name = dto.getName();
        this.tasks = dto.getTasks();
        this.completed = dto.getCompleted();
        this.date = dto.getDate();
    }
}
