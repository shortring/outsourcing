package com.outsourcing.common.aop;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
import com.outsourcing.domain.task.dto.response.TaskResponse;
import com.outsourcing.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

// 관점 (단위)
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final TaskRepository taskRepository;
    private final ActivityRepository activityRepository;

    @Pointcut("execution(* com.outsourcing.domain.comment.service.CommentService.*(..))")
    public void allCommentServiceMethods() {
    }

    @Pointcut("execution(* com.outsourcing.domain.comment.service.CommentService.getComment(..))")
    public void getCommentServiceMethods() {
    }

    // 작업 서비스 포인트컷. 향후 추가 가능.
    @Pointcut("""
            execution(* com.outsourcing.domain.task.service.TaskService.createTaskApi(..))
            || execution(* com.outsourcing.domain.task.service.TaskService.deleteTaskApi(..))""")
    public void createAndDeleteTaskMethod() {
    }

    /**
     * 저장해줄 정보
     * id, type, userId, userInfo, taskId, timestamp, description(ex : 새 작업을 생성하였습니다.)
     *
     */
    @Around("(allCommentServiceMethods() && !getCommentServiceMethods()) || createAndDeleteTaskMethod()")
    public Object afterReturningTaskAop(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        // 저장할 정보들
        ActivityType status = null;
        Task task = null;
        User user = new User(userId);
        String description = null;

        switch (methodName) {
            case "createTaskApi":
                TaskResponse taskResponse = (TaskResponse) result;
                task = taskRepository.getReferenceById(taskResponse.getId());
                description = "새로운 작업 \"" + taskResponse.getTitle() + "\"을 생성했습니다.";
                status = ActivityType.TASK_CREATED;

                break;

            case "deleteTaskApi":
                Object[] request = joinPoint.getArgs();
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
                task = new Task(commentResponses.getTaskId());
                description = "댓글을 수정하였습니다.";
                status = ActivityType.COMMENT_UPDATED;

                break;

            case "deleteComment":
                Object[] requests = joinPoint.getArgs();
                task = new Task((Long) requests[0]);
                description = "댓글을 삭제했습니다.";
                status = ActivityType.COMMENT_DELETED;

        }

        activityRepository.save(Activity.of(status, Instant.now(), description, user, task));

        return result;
    }

}
