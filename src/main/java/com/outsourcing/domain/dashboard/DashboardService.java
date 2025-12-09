package com.outsourcing.domain.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TempTaskRepository taskRepository;

    @Transactional
    public StatsDashboardResponse stats(){

        return new StatsDashboardResponse(null,null,null,null,null,null,null,null);
    }
    @Transactional
    public List<SummaryTaskResponse> myTaskSummary(){
        List<SummaryTaskResponse> task = new ArrayList<SummaryTaskResponse>();
        task.add(new SummaryTaskResponse(null,null,null,null,null));
        return task;
    }
    @Transactional
    public WeeklyTaskTrendDashboardResponse weeklyTaskTrend(){
        return new WeeklyTaskTrendDashboardResponse(null,null,null);
    }
}
