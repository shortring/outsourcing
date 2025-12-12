package com.outsourcing.common.aop;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.comment.model.response.CreateCommentResponse;
import com.outsourcing.domain.comment.model.response.UpdateCommentResponse;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // 포인트 컷
    // 패키지 기반 포인트컷
//    @Pointcut("execution(* com.outsourcing.domain.activities.service..*(..))") // (경로 안의 모든 클래스 파일 안의 모든 메서드를 범위로 지정하겠다라는 의미)
//    public void serviceLayerPointCut() {
//
//    }

    // 어노테이션 기반 포인트컷
//    @Pointcut("@annotation(com.outsourcing.common.aop.CreateLog)")
//    public void trackTimePointCut() {
//
//    }
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

    // 댓글 서비스 레이어 포인트컷
//    @Pointcut("@annotation(com.outsourcing.common.aop.CreateLog)")
//    public void commentServiceLayerPointCut() {
//
//    }

    // 어드바이스 (횡단 관심사)
    // before
//    @Before("serviceLayerPointCut()")
//    public void beforeAop() {
//        log.info("@before - 핵심 기능 전에 수행됨");
//    }

    // afterReturning (아마 여기서 진행해야할 거 같은데)
//    @AfterReturning(pointcut = "serviceLayerPointCut()", returning = "result")
//    public void afterReturningAop(Object result) {
//        log.info("@afterReturning - 핵심기능이 성공한 후 동작");
//        log.info("result : {}", result.getClass());     // 핵심기능에서 반환한 객체
//    }

    // afterThrowing
//    @AfterThrowing("serviceLayerPointCut()", throwing = "exception")
//    public void afterThrowingAop(Throwable exception) {
//        log.info("@AfterThrowing - 핵심기능에서 에러가 발생했을때만 동작");
//        log.info("exception : {}", exception.getClass());
//    }

    // after
    // 실행 성공에 관계없이 실행
//    @After("serviceLayerPointCut()")
//    public void afterAop() {
//        log.info("@After - 묻지도 따지지도않고 실행");
//    }

    // around
    // 에러 발생시, 묻다 실행
//    @Around("trackTimePointCut()")
//    public Object aroundAop(ProceedingJoinPoint joinPoint) {
//        try {
//            //Object result = joinPoint.proceed();    // joinPoint.proceed는 핵심기능 실행 지점
//            log.info("AfterReturning");
//            return result;
//        } catch (RuntimeException exception) {
//            log.info("AfterThrowing");
////            throw new Exception();
//        } finally {
//            log.info("After");
//        }
//
//    }

    // 작업 서비스 aop
    // pointcut = "taskServiceLayerPointCut()", returning = "result"

    /**
     * 저장해줄 정보
     * id, type, userId, userInfo, taskId, timestamp, description(ex : 새 작업을 생성하였습니다.)
     *
     */
    @Around("(allCommentServiceMethods() && !getCommentServiceMethods()) || createAndDeleteTaskMethod()")
    public Object afterReturningTaskAop(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        String className = joinPoint.getSignature().getClass().getSimpleName();
        // 저장할 정보들
        ActivityType status = null;
        Task task = null;
        User user = null;
        String description = null;

        if (className.equals("TaskService")) {
            switch (joinPoint.getSignature().getName()) {
                case "createTask":
                    CreateCommentResponse response = (CreateCommentResponse) result;
                    task = taskRepository.getReferenceById(response.getTaskId());
                    user = userRepository.getReferenceById(response.getUserId());
                    description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                    status = ActivityType.COMMENT_CREATED;

                    break;

                case "deleteTaskApi":
                    Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();

                    Object[] request = joinPoint.getArgs();
                    task = taskRepository.getReferenceById((Long) request[0]);
                    user = userRepository.getReferenceById(userId);
                    description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                    status = ActivityType.TASK_DELETED;

            }
        }

        if (className.equals("CommentService")) {
            switch (joinPoint.getSignature().getName()) {
                case "createCommentApi":
                    if (result instanceof CreateCommentResponse) {
                        CreateCommentResponse response = (CreateCommentResponse) result;
                        task = taskRepository.getReferenceById(response.getTaskId());
                        user = userRepository.getReferenceById(response.getUserId());
                        description = "작업 \"" + task.getTitle() + "에 댓글을 작성하였습니다.";
                        status = ActivityType.COMMENT_CREATED;

                    }
                    break;

                case "updateCommentApi":
                    UpdateCommentResponse response = (UpdateCommentResponse) result;
                    task = taskRepository.getReferenceById(response.getTaskId());
                    user = userRepository.getReferenceById(response.getUserId());
                    description = "댓글을 수정하였습니다.";
                    status = ActivityType.COMMENT_UPDATED;

                    break;

                case "deleteCommentApi":
                    Object[] request = joinPoint.getArgs();
                    task = taskRepository.getReferenceById((Long) request[0]);
                    user = userRepository.getReferenceById((Long) request[1]);
                    description = "댓글을 삭제했습니다.";
                    status = ActivityType.COMMENT_DELETED;

            }

        }

        activityRepository.save(Activity.of(status, Instant.now(), description, user, task));
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//
//        CreateLog createLog = methodSignature.getMethod().getAnnotation(CreateLog.class);
//
//        try {
//            Object result = joinPoint.proceed();
//
//            // Activity 만들어서 repository에 넣어줘야됨
//
//            // 타입 받아오기
//            ActivityType type = createLog.type();
//
//            // userId 받아오기
//            joinPoint.getArgs();
//
//            //timestamp 찍기
//            Instant timestamp = Instant.now();
//
//            // taskId 받아오기
//
//            // 연관관계 객체 받아오기
//            Task task = (Task) result;
//            UserDto userDto = UserDto.from((User) result);
//
//            // timestamp 받아오기
//            // taskStatus 받아오기
//            TaskStatus pastTaskStatus = TaskStatus.IN_PROGRESS; //임시
//            TaskStatus updatedTaskStatus = TaskStatus.DONE;     //임시
//
//            // description 생성해서 넣어주기
//            String description = "";
//            switch(type) {
//                case TASK_CREATED:
//                    description = "새로운 작업 \"" + task.getTitle() + "\"을 생성했습니다.";
//                    break;
//                case TASK_UPDATED:
//                    description = "작업 \"" + task.getTitle() + "\"정보를 수정했습니다.";
//                    break;
//                case TASK_DELETED:
//                    description = "작업 " + "테스트 작업" + "을 삭제했습니다.";
//                    break;
//                case TASK_STATUS_CHANGED:
//                    description = "작업 상태를 " + pastTaskStatus.toString() + "에서 " + updatedTaskStatus.toString() + "로 변경했습니다.";
//                    break;
//                case COMMENT_CREATED:
//                    description = "작업 " + "\"대시보드 UI 디자인\"" + "에 댓글을 작성했습니다.";
//                    break;
//                case COMMENT_UPDATED:
//                    description = "댓글을 수정했습니다.";
//                    break;
//                case COMMENT_DELETED:
//                    description = "댓글을 삭제했습니다.";
//                    break;
//            }
//        return null;
        return result;
    }

}
