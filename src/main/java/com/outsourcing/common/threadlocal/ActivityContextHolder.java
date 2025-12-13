package com.outsourcing.common.threadlocal;

import com.outsourcing.common.enums.ActivityDraft;
import com.outsourcing.domain.activities.dto.ActivityType;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
public final class ActivityContextHolder {

    private static final ThreadLocal<ActivityDraft> CTX= new ThreadLocal<>();

    // 1. 시작
    public static void init(ActivityType type, Long taskId, Long userId){
        CTX.set(new ActivityDraft(type, taskId, userId, null, Instant.now()));

    }

    // 2. 상세 메시지
    public static void setDescription(String description){
        ActivityDraft cur=CTX.get();
        if(cur==null) return;
        CTX.set(cur.withDescription(description));
    }

    public static void setTaskId(Long taskId){
        ActivityDraft cur=CTX.get();
        if(cur==null) return;
        CTX.set(cur.withTaskId(taskId));
    }
    // 3. get
    public static ActivityDraft get(){
        return CTX.get();
    }

    // 4. clear
    public static void clear(){
        CTX.remove();
    }
}
