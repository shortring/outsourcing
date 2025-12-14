package com.outsourcing.domain.task.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.task.dto.request.CreateTaskRequest;
import com.outsourcing.domain.task.dto.response.TaskDetailResponse;
import com.outsourcing.domain.task.dto.response.TaskResponse;
import com.outsourcing.domain.task.dto.request.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.request.UpdateTaskStatusRequest;
import com.outsourcing.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // 작업 생성
    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTaskApi(
            @Valid @RequestBody CreateTaskRequest request
    ) {
        TaskResponse data = taskService.createTaskApi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("작업이 생성되었습니다.", data)
        );
    }

    // 작업 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskApi(
                                                                   @PathVariable Long taskId,
                                                                   @Valid @RequestBody UpdateTaskRequest request
    ) {
        TaskResponse data = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(ApiResponse.success("작업이 수정되었습니다.", data));
    }

    // 작업 상태 변경
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskStatusApi(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {

        TaskResponse data = taskService.updateTaskStatus(taskId, request, userDetails.getUserId());

        return ResponseEntity.ok(ApiResponse.success("작업 상태가 변경되었습니다.", data));
    }

    // 작업 제거
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTaskApi(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(ApiResponse.success("작업이 삭제되었습니다.", null));
    }

    // 작업 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDetailResponse>> getTaskApi(@PathVariable Long taskId) {
        TaskDetailResponse data = taskService.getTask(taskId);
        return ResponseEntity.ok(ApiResponse.success("작업 조회 성공", data));
    }

    // 작업 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<TaskResponse>>> getListTasksApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, name = "query") String query,
            @RequestParam(required = false) Long assigneeId

    ) {
        String keyword = (search != null && !search.isBlank())
                ? search
                : query;
        PagedResponse<TaskResponse> data = taskService.getListTask(
                page, size, status, keyword, assigneeId
        );
        return ResponseEntity.ok(ApiResponse.success("작업 목록 조회 성공", data));
    }
}

