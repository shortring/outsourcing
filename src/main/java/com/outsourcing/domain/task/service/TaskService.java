package com.outsourcing.domain.task.service;

import com.outsourcing.common.dto.PageCondition;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.UserRole;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.task.dto.CreateTaskRequest;
import com.outsourcing.domain.task.dto.TaskResponse;
import com.outsourcing.domain.task.dto.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.UpdateTaskStatusRequest;
import com.outsourcing.domain.task.repository.TaskQueryRepository;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;

import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskQueryRepository taskQueryRepository;


    @Transactional
    public TaskResponse createTaskApi(CreateTaskRequest request) {


        User assigneeUser = userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_ASSIGNEE));

        Task task = new Task(
                request.title(),
                request.description(),
                request.priority(),
                assigneeUser,
                request.dueDate()
        );

        task.changeStatus(TaskStatus.TODO);

        taskRepository.save(task);

        return TaskResponse.from(task);
    }

    // Task 수정 요청을 하면 updatedAt이 변경됨.
    @Transactional
    public TaskResponse updateTaskApi(Long currentUserId, Long taskId, UpdateTaskRequest request) {
        Instant now = Instant.now();

        User requestUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        if (!ObjectUtils.nullSafeEquals(requestUser.getRole(), UserRole.ADMIN)) {
            throw new CustomException(ErrorMessage.FORBIDDEN_NO_PERMISSION_UPDATE);
        }

        User assigneeUser = userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_ASSIGNEE));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));


        task.update(
                request.title(),
                request.description(),
                request.priority(),
                assigneeUser,
                request.dueDate(),
                now
        );

        return TaskResponse.from(task);
    }

    // Status를 변경해도 수정일 갱신은 되지 않음.
    @Transactional
    public TaskResponse updateTaskStatusApi(Long taskId, UpdateTaskStatusRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        task.changeStatus(request.status());

        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTaskApi(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TASK));

        taskRepository.delete(task);
    }

    // email
    @Transactional(readOnly = true)
    public TaskResponse getTaskApi(Long taskId) {
        Task task = taskRepository.findById(taskId)
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

        // 2. 예외처리
        if (pageCondition.size() == 0) {
            throw new CustomException(ErrorMessage.BAD_REQUEST_WRONG_PARAM);
        }

        // 3. -> Pageable
        Pageable pageable = PageRequest.of(pageCondition.page(), pageCondition.size());

        // 4. Task -> TaskResponse
        Page<Task> taskPage = taskQueryRepository.search(pageable, status, keyword, assigneeId);
        Page<TaskResponse> responseDto = taskPage.map(TaskResponse::from);

        // 5. TaskResponse -> PagedResponse
        return PagedResponse.from(responseDto);
    }
}
