package com.outsourcing.domain.task.service;

import com.outsourcing.common.dto.PageCondition;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.DataStatus;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.task.dto.TaskDetailDto;
import com.outsourcing.domain.task.dto.TaskDto;
import com.outsourcing.domain.task.dto.request.CreateTaskRequest;
import com.outsourcing.domain.task.dto.request.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.request.UpdateTaskStatusRequest;
import com.outsourcing.domain.task.dto.response.TaskDetailResponse;
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

import java.time.Instant;
import java.time.ZoneId;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskQueryRepository taskQueryRepository;
    private final ActivityRepository activityRepository;
    private static final ZoneId KOREA = ZoneId.of("Asia/Seoul");

    // 작업 생성
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {


        User assigneeUser = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_ASSIGNEE));

        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                assigneeUser,
                request.getDueDate().atZone(KOREA).toInstant()
        );

        task.changeStatus(TaskStatus.TODO);

        taskRepository.save(task);
        TaskDto taskDto = TaskDto.from(task);

        return TaskResponse.from(taskDto);
    }

    // 작업 수정
    @Transactional
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request) {
        Instant now = Instant.now();

        User assigneeUser = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_ASSIGNEE));

        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));


        task.update(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                assigneeUser,
                request.getDueDate().atZone(KOREA).toInstant(),
                now
        );

        TaskDto taskDto = TaskDto.from(task);

        return TaskResponse.from(taskDto);
    }

    // 작업 상태 변경
    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, UpdateTaskStatusRequest request, Long userId) {

        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        if (!request.status().toString().equals(task.getStatus().toString())) {
            User user = userRepository.getReferenceById(userId);
            String description = "작업 상태를 " + task.getStatus().toString() + "에서 " + request.status() + "으로 변경했습니다.";

            activityRepository.save(Activity.of(ActivityType.TASK_STATUS_CHANGED, Instant.now(), description, user, task));
        }
        task.changeStatus(request.status());

        TaskDto taskDto = TaskDto.from(task);

        return TaskResponse.from(taskDto);
    }

    // 작업 제거
    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        task.isArchived();
    }

    // 작업 조회
    @Transactional(readOnly = true)
    public TaskDetailResponse getTask(Long taskId) {
        Task task = taskRepository.findByIdAndDataStatus(taskId, DataStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        TaskDetailDto taskDto = TaskDetailDto.from(task);
        return TaskDetailResponse.of(taskDto);
    }

    // 작업 목록 조회
    @Transactional(readOnly = true)
    public PagedResponse<TaskResponse> getListTask(
            int rawPage,
            int rawSize,
            TaskStatus status,
            String keyword,
            Long assigneeId
    ) {

        PageCondition pageCondition = PageCondition.of(rawPage, rawSize);

        if (pageCondition.size() == 0) {
            throw new CustomException(ErrorMessage.BAD_REQUEST_WRONG_PARAM);
        }

        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        Page<Task> taskPage = taskQueryRepository.search(pageable, status, keyword, assigneeId);
        Page<TaskDto> responseDto = taskPage.map(TaskDto::from);

        Page<TaskResponse> response = responseDto.map(TaskResponse::from);

        return PagedResponse.from(response);
    }
}
