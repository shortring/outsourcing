package com.outsourcing.domain.task.service;

import com.outsourcing.common.dto.PageCondition;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.domain.task.dto.CreateTaskRequest;
import com.outsourcing.domain.task.dto.TaskResponse;
import com.outsourcing.domain.task.dto.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.UpdateTaskStatusRequest;
import com.outsourcing.domain.task.repository.TaskQueryRepository;
import com.outsourcing.domain.task.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskQueryRepository taskQueryRepository;


    @Transactional
    public TaskResponse createTask(CreateTaskRequest request){

       User assigneeUser=userRepository.findById(request.assigneeId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        Task task=new Task(
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
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request){
        Instant now = Instant.now();

        User assigneeUser=userRepository.findById(request.assigneeId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        Task task=taskRepository.findById(taskId)
                .orElseThrow(()->new IllegalArgumentException("Task not found"));



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
    public TaskResponse updateTaskStatus(Long taskId, UpdateTaskStatusRequest request){

        Task task=taskRepository.findById(taskId)
                .orElseThrow(()->new IllegalArgumentException("Task not found"));

        task.changeStatus(request.status());

        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTask(Long taskId){
        Task task=taskRepository.findById(taskId)
                .orElseThrow(()->new IllegalArgumentException("Task not found"));

        taskRepository.delete(task);
    }

    // email
    @Transactional(readOnly=true)
    public TaskResponse getTask(Long taskId){
        Task task=taskRepository.findById(taskId)
                .orElseThrow(()->new IllegalArgumentException("Task not found"));

        return TaskResponse.from(task);
    }

    @Transactional(readOnly=true)
    public PagedResponse<TaskResponse> getListTask(
            int rawPage,
            int rawSize,
            TaskStatus status,
            String keyword,
            Long assigneeId
    ){

        // 1. pageCondition : 정규화.
        PageCondition pageCondition=PageCondition.of(rawPage, rawSize);

        // 2. -> Pageable
        Pageable pageable= PageRequest.of(pageCondition.page(),  pageCondition.size());

        // 3. Task -> TaskResponse
        Page<Task> taskPage=taskQueryRepository.search(pageable, status, keyword, assigneeId);
        Page<TaskResponse> responseDto=taskPage.map(TaskResponse::from);

        // 4. TaskResponse -> PagedResponse
        return PagedResponse.from(responseDto);
    }
}
