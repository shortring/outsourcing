package com.outsourcing.common.aop;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
import com.outsourcing.domain.task.dto.request.UpdateTaskRequest;
import com.outsourcing.domain.task.dto.response.TaskResponse;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @Pointcut("execution(* com.outsourcing.domain.comment.service.CommentService.*(..))")
    public void allCommentServiceMethods() {
    }

    @Pointcut("execution(* com.outsourcing.domain.comment.service.CommentService.getComment(..))")
    public void getCommentServiceMethods() {
    }

    @Pointcut("""
            execution(* com.outsourcing.domain.task.service.TaskService.createTask(..))
            || execution(* com.outsourcing.domain.task.service.TaskService.updateTask(..))
            || execution(* com.outsourcing.domain.task.service.TaskService.deleteTask(..))""")
    public void createAndDeleteTaskMethod() {
    }

    //Task 및 Comment 관련 작업 수행 후 사용자 활동 로그를 기록
    @Around("(allCommentServiceMethods() && !getCommentServiceMethods()) || createAndDeleteTaskMethod()")
    public Object afterReturningTaskAop(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();

        User user = userRepository.getReferenceById(userId);

        ActivityType status = null;
        Task task = null;
        String description = null;
        Object[] request = joinPoint.getArgs();

        switch (methodName) {

            case "createTask":
                TaskResponse taskResponse = (TaskResponse) result;
                task = taskRepository.getReferenceById(taskResponse.getId());
                description = "새로운 작업 \"" + taskResponse.getTitle() + "\"을 생성했습니다.";
                status = ActivityType.TASK_CREATED;
                break;

            case "updateTask":
                task = taskRepository.findById((Long) request[0]).orElseThrow();
                description = "작업 \"" + ((UpdateTaskRequest) request[1]).getTitle() + "\" 정보를 수정했습니다.";
                status = ActivityType.TASK_UPDATED;
                break;

            case "deleteTask":
                task = taskRepository.findById((Long) request[0]).orElseThrow();
                description = "작업 \"" + task.getTitle() + "\"을 삭제했습니다.";
                status = ActivityType.TASK_DELETED;
                break;

            case "createComment":
                CreateCommentResponse commentResponse = (CreateCommentResponse) result;
                task = taskRepository.findById(commentResponse.getTaskId()).orElseThrow();
                description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                status = ActivityType.COMMENT_CREATED;
                break;

            case "updateComment":
                UpdateCommentResponse commentResponses = (UpdateCommentResponse) result;
                task = taskRepository.getReferenceById(commentResponses.getTaskId());
                description = "댓글을 수정하였습니다.";
                status = ActivityType.COMMENT_UPDATED;

                break;

            case "deleteComment":
                task = taskRepository.getReferenceById((Long) request[0]);
                description = "댓글을 삭제했습니다.";
                status = ActivityType.COMMENT_DELETED;
        }

        activityRepository.save(Activity.of(status, Instant.now(), description, user, task));

        return result;
    }
}
