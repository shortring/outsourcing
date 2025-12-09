package com.outsourcing.domain.dashboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WeeklyTaskTrendDashboardResponse {
    private final String[] days;
    private final Long[] completed;
    private final Long[] created;
}
