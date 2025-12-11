package com.outsourcing.domain.task.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.dto.PagedResponse;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.task.dto.CreateTaskRequest;
import com.outsourcing.domain.task.dto.TaskResponse;
import com.outsourcing.domain.task.dto.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.UpdateTaskStatusRequest;
import com.outsourcing.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTaskApi(
            @Valid @RequestBody CreateTaskRequest request
    ) {
        TaskResponse data = taskService.createTaskApi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("작업이 생성되었습니다.", data)
        );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskApi(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                   @PathVariable Long taskId,
                                                                   @Valid @RequestBody UpdateTaskRequest request
    ) {
        TaskResponse data = taskService.updateTaskApi(userDetails.getUserId(), taskId, request);
        return ResponseEntity.ok(ApiResponse.success("작업이 수정되었습니다.", data));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskStatusApi(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        TaskResponse data = taskService.updateTaskStatusApi(taskId, request);
        return ResponseEntity.ok(ApiResponse.success("작업 상태가 변경되었습니다.", data));
    }

    /*
    export const del = <T>(url: string, config?: AxiosRequestConfig) =>
        apiRequest<T>('DELETE', url, config); ??
  */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTaskApi(@PathVariable Long taskId) {
        taskService.deleteTaskApi(taskId);
        return ResponseEntity.ok(ApiResponse.success("작업이 삭제되었습니다.", null)); // 오버로딩 -> null
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskApi(@PathVariable Long taskId) {
        TaskResponse data = taskService.getTaskApi(taskId);
        return ResponseEntity.ok(ApiResponse.success("작업 조회 성공", data));
    }

    // Get + QueryParam.
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<TaskResponse>>> getListTasksApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, name = "query") String query, // FE 정합성.
            @RequestParam(required = false) Long assigneeId

    ) {
        String keyword = (search != null && !search.isBlank()) // search - query 정합성.
                ? search
                : query;
        PagedResponse<TaskResponse> data = taskService.getListTaskApi(
                page, size, status, keyword, assigneeId
        );
        return ResponseEntity.ok(ApiResponse.success("작업 목록 조회 성공", data));
    }
}

