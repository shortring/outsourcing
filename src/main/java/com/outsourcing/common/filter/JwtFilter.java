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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    //화이트리스트 추가
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return path.equals("/api/auth/login") || (path.equals("/api/users") && method.equals("POST"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //authorizationHeader  유/무 검사
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("Jwt 토큰이 필요 합니다.");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt 토큰이 필요 합니다.");
//            return;
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(7);

        // 토큰 유효성 검사
        if (!jwtUtil.validateToken(jwt)) {
            log.info("Jwt 토큰이 유효하지 않습니다.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);//유효하지 않아
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
            return;
        }

        String userName = jwtUtil.extractUsername(jwt);

        String auth = jwtUtil.extractRole(jwt);

        Long userId = jwtUtil.extractUserId(jwt);

        UserRole userRole = UserRole.valueOf(auth);

        //Spring Security에서 사용하는 User 객체를 생성했습니다.
        CustomUserDetails userDetails = new CustomUserDetails(
                userId,
                userName,
                "N/A",
                List.of(new SimpleGrantedAuthority(userRole.getRole()))
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }
}
