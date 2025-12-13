package com.outsourcing.common.aop;


import com.outsourcing.common.dto.ActivityCreatedEvent;
import com.outsourcing.common.enums.ActivityDraft;
import com.outsourcing.common.threadlocal.ActivityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.outsourcing.common.filter.CustomUserDetails;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLogAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Around("@annotation(activityLog)")
    public Object around(
            ProceedingJoinPoint joinPoint,
            ActivityLog activityLog) throws Throwable {
        Object[] task=joinPoint.getArgs();
        Long taskId = extractTaskId(task);
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Long userId=null;
        if(authentication!=null
                &&authentication.isAuthenticated()
                &&!(authentication instanceof AnonymousAuthenticationToken)
                &&authentication.getPrincipal() instanceof CustomUserDetails userDetails
        ){
            userId=userDetails.getUserId();
        }

        ActivityContextHolder.init(activityLog.type(),taskId,userId);

        try{
            Object result=joinPoint.proceed();
            ActivityDraft activityDraft=ActivityContextHolder.get();
            if(activityDraft!=null
                    &&activityDraft.description()!=null) {
                ActivityCreatedEvent event=ActivityCreatedEvent.of(activityDraft);
                applicationEventPublisher.publishEvent(event);
            }
            return result;
        }finally{
            ActivityContextHolder.clear();
        }
    }


    private Long extractTaskId(Object[] args){

        // 1. null
        if(args==null) return null;

        // 2. PathVariable : taskId가 Long 또는 Integer에 있는 경우
        for(Object arg : args){
            if(arg instanceof Long l) return l;
            if(arg instanceof Integer i) return i.longValue();
        }

        // 3. DTO랑 Record 안에 taskId 있는 경우
        for(Object arg : args){
            if(arg==null) continue;
            try{
                var m=arg.getClass().getMethod("taskId");
                Object value = m.invoke(arg);
                if(value instanceof Long l) return l;
            }catch(NoSuchMethodException ignored){
            }catch(Exception e){
                log.warn("[WARNING] Exception={}", e.getMessage());
            }

            try{
                var m=arg.getClass().getMethod("getTaskId");
                Object value = m.invoke(arg);
                if(value instanceof Long l) return l;
            }catch(NoSuchMethodException ignored){
            }catch(Exception e){
                log.warn("[WARNING] Exception={}", e.getMessage());
            }
        }

        return null;
    }
}
