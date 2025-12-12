package com.outsourcing.common.aop;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.UserRole;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
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

// 관점 (단위)
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    UserRepository userRepository;
    TaskRepository taskRepository;
    ActivityRepository activityRepository;

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

    // 작업 서비스 레이어 포인트컷
    @Pointcut("@annotation(com.outsourcing.common.aop.CreateLog)")
    public void taskServiceLayerPointCut() {
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
        User user = null;
        String description = null;

        switch (methodName) {
            case "createTaskApi":

                TaskResponse response = (TaskResponse) result;
                task = taskRepository.getReferenceById(response.getId());
                user = userRepository.getReferenceById(userId);
                description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                status = ActivityType.COMMENT_CREATED;

                break;

            case "deleteTaskApi":

                Object[] request = joinPoint.getArgs();
                task = taskRepository.getReferenceById((Long) request[0]);
                user = userRepository.getReferenceById(userId);
                description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                status = ActivityType.TASK_DELETED;
                break;

            case "createCommentApi":
                if (result instanceof CreateCommentResponse) {
                    CreateCommentResponse commentResponse = (CreateCommentResponse) result;
                    task = taskRepository.getReferenceById(commentResponse.getTaskId());
                    user = userRepository.getReferenceById(commentResponse.getUserId());
                    description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                    status = ActivityType.COMMENT_CREATED;

                }
                break;

            case "updateCommentApi":
                UpdateCommentResponse responses = (UpdateCommentResponse) result;
                task = taskRepository.getReferenceById(responses.getTaskId());
                user = userRepository.getReferenceById(responses.getUserId());
                description = "댓글을 수정하였습니다.";
                status = ActivityType.COMMENT_UPDATED;

                break;

            case "deleteCommentApi":
                Object[] requests = joinPoint.getArgs();
                task = taskRepository.getReferenceById((Long) requests[0]);
                user = userRepository.getReferenceById((Long) requests[1]);
                description = "댓글을 삭제했습니다.";
                status = ActivityType.COMMENT_DELETED;

        }

        if (user != null)
            activityRepository.save(Activity.of(status, Instant.now(), description, user, task));

        return result;
    }

}
