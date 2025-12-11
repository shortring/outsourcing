package com.outsourcing.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
