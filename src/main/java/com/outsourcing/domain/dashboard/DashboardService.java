package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final TempTeamRepository teamRepository;

    @Transactional
    public StatsDashboardResponse stats(Long id) {
        List<DashboardDto> dtos = dashboardRepository.findTaskStatus();
        Optional<Team> team = teamRepository.findById(1L);
        long total = dtos.size();
        long complete = 0, inProgress = 0, todo = 0, overdue = 0, myTasksToday = 0;
        long teamProgress;

        LocalDate today = LocalDate.now();

        for (DashboardDto dto : dtos) {
            if (!dto.getStatus().equals(TaskStatus.DONE) &&
                    dto.getDueDate().isBefore(today)) {
                ++overdue;
                continue;
            }

            if (dto.getAssigneeId().equals(id) && dto.getDueDate().equals(today)) ++myTasksToday;

            switch (dto.getStatus()) {
                case DONE:
                    ++complete;
                    break;
                case TODO:
                    ++todo;
                    break;
                case IN_PROGRESS:
                    ++inProgress;
                    break;
            }
        }
        long completionRate = Math.round(complete / total * 10000) / 100;
        return new StatsDashboardResponse(total, complete, inProgress, todo, overdue, null, myTasksToday, completionRate);
    }

    @Transactional
    public List<SummaryTaskResponse> myTaskSummary() {
        List<SummaryTaskResponse> task = new ArrayList<SummaryTaskResponse>();
        task.add(new SummaryTaskResponse(null, null, null, null, null));
        return task;
    }

    @Transactional
    public WeeklyTaskTrendDashboardResponse weeklyTaskTrend() {
        return new WeeklyTaskTrendDashboardResponse(null, null, null);
    }
}
