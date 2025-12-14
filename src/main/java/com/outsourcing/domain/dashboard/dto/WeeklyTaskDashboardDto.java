package com.outsourcing.domain.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeeklyTaskDashboardDto {
    private String name;
    private Long tasks;
    private Long completed;
    private LocalDate date;

    public WeeklyTaskDashboardDto(String name, Long tasks, Long completed, LocalDate date) {
        this.name = name;
        this.tasks = tasks;
        this.completed = completed;
        this.date = date;
    }

    public static WeeklyTaskDashboardDto dateSet(String name, LocalDate date) {
        return new WeeklyTaskDashboardDto(name, 0L, 0L, date);
    }
}
