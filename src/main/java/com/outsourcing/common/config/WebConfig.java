package com.outsourcing.common.config;

import com.outsourcing.common.aop.LoggingAspect;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import com.outsourcing.common.aop.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// aspect를 등록하여 읽을 수 있게 처리
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins) //허용할 도매인 설정
                .allowedMethods("*")            // 허용할 http 메소드 설정
                .allowedHeaders("*")            // 허용할 헤더 설정
                .allowCredentials(true)         // 인증정보 허용 여부
                .maxAge(3600);                  //preflight 요청의 유효시간 설정(설정 안 해도됨, 기본적으로 30븐이 설정 되어있음)
    }

//    @Bean
//    public LoggingAspect aspectConfig() {
//        return new LoggingAspect(TaskRepository, ActivityRepository);
//    }
}
