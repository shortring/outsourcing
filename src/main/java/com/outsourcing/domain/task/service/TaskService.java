package com.outsourcing.domain.task.service;

import com.outsourcing.common.dto.PageCondition;
import com.outsourcing.common.dto.PagedResponse;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.DataStatus;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;

import com.outsourcing.common.threadlocal.ActivityContextHolder;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.task.dto.request.CreateTaskRequest;
import com.outsourcing.domain.task.dto.request.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.request.UpdateTaskStatusRequest;

import com.outsourcing.domain.task.dto.response.TaskResponse;
import com.outsourcing.domain.task.repository.TaskQueryRepository;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.outsourcing.common.aop.ActivityLog;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskQueryRepository taskQueryRepository;
    private static final ZoneId KOREA = ZoneId.of("Asia/Seoul");

    @ActivityLog(type = ActivityType.TASK_CREATED)
    @Transactional
    public TaskResponse createTaskApi(CreateTaskRequest request) {


        User assigneeUser = userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_ASSIGNEE));

        Task task = new Task(
                request.title(),
                request.description(),
                request.priority(),
                assigneeUser,
                request.dueDate().atZone(KOREA).toInstant()
        );

        task.changeStatus(TaskStatus.TODO);

        taskRepository.save(task);

        String desc = "새로운 작업 \"" + task.getTitle() + "\"을 생성했습니다.";
        ActivityContextHolder.setTaskId(task.getId());
        ActivityContextHolder.setDescription(desc); //

        return TaskResponse.from(task);
    }

    // Task 수정 요청을 하면 updatedAt이 변경됨.

    @ActivityLog(type = ActivityType.TASK_UPDATED)
    @Transactional
    public TaskResponse updateTaskApi(Long taskId, UpdateTaskRequest request) {
        Instant now = Instant.now();


        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        String oldTitle = task.getTitle();
        String oldDescription = task.getDescription();
        TaskPriority oldPriority = task.getPriority();
        Long oldAssigneeId = task.getAssignee().getId();
        Instant oldDueDate = task.getDueDate();

        User assigneeUser = userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_ASSIGNEE));

        task.update(
                request.title(),
                request.description(),
                request.priority(),
                assigneeUser,
                request.dueDate().atZone(KOREA).toInstant(),
                now
        );

        List<String> changes = new ArrayList<>();
        if(!Objects.equals(oldTitle, task.getTitle())) changes.add("제목");
        if(!Objects.equals(oldDescription, task.getDescription())) changes.add("내용");
        if(!Objects.equals(oldPriority, task.getPriority())) changes.add("우선순위");
        if(!Objects.equals(oldAssigneeId, task.getAssigneeId())) changes.add("담당자");
        if(!Objects.equals(oldDueDate, task.getDueDate())) changes.add("마감일");

        if(!changes.isEmpty()){
            String desc = "작업 \"" + task.getTitle() + "\"정보를 수정했습니다.";
            ActivityContextHolder.setDescription(desc);
        }

        return TaskResponse.from(task);
    }


    // Status를 변경해도 수정일 갱신은 되지 않음.
    @ActivityLog(type = ActivityType.TASK_STATUS_CHANGED)
    @Transactional
    public TaskResponse updateTaskStatusApi(Long taskId, UpdateTaskStatusRequest request) {

        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        if(!Objects.equals(task.getStatus(), request.status())){
            String desc = "작업 상태를 \"" + request.status().name() + "\"에서 \"" + task.getStatus().name() + "\"로 변경했습니다.";
            ActivityContextHolder.setDescription(desc); //
        }
        task.changeStatus(request.status());

        return TaskResponse.from(task);
    }

    @ActivityLog(type = ActivityType.TASK_DELETED)
    @Transactional
    public void deleteTaskApi(Long taskId) {
        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        String desc = "작업 \"" + task.getTitle() + "\"을 삭제했습니다.";
        ActivityContextHolder.setDescription(desc); //
        task.isArchived();
    }

    // email
    @Transactional(readOnly = true)
    public TaskResponse getTaskApi(Long taskId) {
        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));


        return TaskResponse.from(task);
    }

    @Transactional(readOnly = true)
    public PagedResponse<TaskResponse> getListTaskApi(
            int rawPage,
            int rawSize,
            TaskStatus status,
            String keyword,
            Long assigneeId
    ) {

        // 1. pageCondition : 정규화.
        PageCondition pageCondition = PageCondition.of(rawPage, rawSize);

        // 2. -> Pageable
        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        // 3. Task -> TaskResponse
        Page<Task> task = taskQueryRepository.search(pageable, status, keyword, assigneeId);
        Page<TaskResponse> response=task.map(TaskResponse::from);

        // 4. TaskResponse -> PagedResponse
        return PagedResponse.from(response);
    }
}
