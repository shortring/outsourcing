package com.outsourcing.common.filter;


import com.outsourcing.common.enums.UserRole;
import com.outsourcing.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //토큰을 발급 받는 로그인의 경우에는 토큰 검사를 하지 않아도 통과
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/api/login") || requestURI.equals("/api/users")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 유무 검사
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            log.info("Jwt 토큰이 필요 합니다.");
            throw new RuntimeException("인증이 필요합니다.");
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt 토큰이 필요 합니다.");
            //return;
        }

        String jwt = authorizationHeader.substring(7);

        // 토큰 유효성 검사
        if (!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);//유효하지 않아
            response.getWriter().write("{\"error\": \"Unauthorized\"}");

        }

        String userName = jwtUtil.extractUsername(jwt);

        String auth = jwtUtil.extractRole(jwt);

        UserRole userRole = UserRole.valueOf(auth);

        //Spring Security에서 사용하는 User 객체를 생성했습니다.
        User user = new User(userName, "N/A", List.of(userRole::getRole));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        filterChain.doFilter(request, response);


    }
}
