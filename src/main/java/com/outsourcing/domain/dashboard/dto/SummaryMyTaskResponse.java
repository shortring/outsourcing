package com.outsourcing.domain.dashboard.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SummaryMyTaskResponse {
    private final List<SummaryMyTaskDto> todayTasks;
    private final List<SummaryMyTaskDto> upcomingTasks;
    private final List<SummaryMyTaskDto> overdueTasks;

    public SummaryMyTaskResponse(List<SummaryMyTaskDto> todayTasks, List<SummaryMyTaskDto> upcomingTasks, List<SummaryMyTaskDto> overdueTasks) {
        this.todayTasks = todayTasks;
        this.upcomingTasks = upcomingTasks;
        this.overdueTasks = overdueTasks;
    }

    public static SummaryMyTaskResponse newResponse() {
        return new SummaryMyTaskResponse(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void addTodayTasks(SummaryMyTaskDto dto) {
        this.todayTasks.add(dto);
    }

    public void addUpcomingTasks(SummaryMyTaskDto dto) {
        this.upcomingTasks.add(dto);
    }

    public void addOverdueTasks(SummaryMyTaskDto dto) {
        this.overdueTasks.add(dto);
    }
}
