package com.outsourcing.common.aop;

import com.outsourcing.domain.activities.dto.ActivityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 런타임 중에 계속 살려둠
public @interface ActivityLog {
    ActivityType type();
}
