package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.task.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final TempTeamRepository teamRepository;
    private final TempTeamMemberRepository teamMemberRepository;

    @Transactional
    public StatsDashboardResponse stats(Long id) {
        List<DashboardDto> dtos = dashboardRepository.findAllTaskStatus();
        Set<Long> teamMembers = teamMemberRepository.findTeamMemberIdsByUserId(id);

        long total = dtos.size(), complete = 0, inProgress = 0, todo = 0, overdue = 0,
                myTasksToday = 0, userTaskTotal = 0, userTaskDone = 0, teamTaskTotal = 0, teamTaskDone = 0;

        LocalDate today = LocalDate.now();

        for (DashboardDto dto : dtos) {
            if (!dto.getStatus().equals(TaskStatus.DONE) &&
                    dto.getDueDate().isBefore(today)) {
                ++overdue;
                continue;
            }

            if (teamMembers.contains(dto.getAssigneeId())) {
                ++teamTaskTotal;
                if (dto.getStatus().equals(TaskStatus.DONE)) ++teamTaskDone;
            }

            if (dto.getAssigneeId().equals(id)){
                ++userTaskTotal;
                if (dto.getDueDate().isEqual(today)) ++myTasksToday;
            }

            switch (dto.getStatus()) {
                case DONE:
                    ++complete;
                    if (dto.getAssigneeId().equals(id)) ++userTaskDone;
                    break;
                case TODO:
                    ++todo;
                    break;
                case IN_PROGRESS:
                    ++inProgress;
                    break;
            }
        }
        long completionRate = userTaskTotal != 0 ? Math.round(userTaskDone / userTaskTotal * 10000) / 100 : 0;
        long teamProgress = teamTaskTotal != 0 ? Math.round(teamTaskDone / teamTaskTotal * 10000) / 100 : 0;
        return new StatsDashboardResponse(total, complete, inProgress, todo, overdue, teamProgress, myTasksToday, completionRate);
    }

    @Transactional
    public SummaryMyTaskResponse myTaskSummary(Long id) {
        List<SummaryMyTaskDto> tasks = dashboardRepository.findAllMyTaskStatusByUserId(id);
        SummaryMyTaskResponse response = SummaryMyTaskResponse.newResponse();
        LocalDate today = LocalDate.now();
        for (SummaryMyTaskDto task: tasks) {
            LocalDate dueDay = LocalDateTime.ofInstant(task.getDueDate(), ZoneId.systemDefault()).toLocalDate();
            if (dueDay.equals(today)) {
                response.addTodayTasks(task);
                continue;
            }
            if (dueDay.isAfter(today)){
                response.addUpcomingTasks(task);
                continue;
            }
            if (dueDay.isBefore(today)) response.addOverdueTasks(task);
        }
        return response;
    }

    @Transactional
    public WeeklyTaskTrendDashboardResponse weeklyTaskTrend() {
        return new WeeklyTaskTrendDashboardResponse(null, null, null);
    }
}
