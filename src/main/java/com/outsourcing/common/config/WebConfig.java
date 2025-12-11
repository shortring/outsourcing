package com.outsourcing.common.config;

import com.outsourcing.common.aop.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// aspect를 등록하여 읽을 수 있게 처리
@Configuration
public class WebConfig {

    @Bean
    public LoggingAspect aspectConfig() {
        return new LoggingAspect();
    }
}
