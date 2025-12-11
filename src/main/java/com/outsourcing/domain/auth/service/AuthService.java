package com.outsourcing.domain.auth.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.common.utils.JwtUtil;
import com.outsourcing.domain.auth.repository.AuthRepository;
import com.outsourcing.domain.auth.model.request.LoginRequest;
import com.outsourcing.domain.auth.model.request.VerifyPasswordRequest;
import com.outsourcing.domain.auth.model.response.VerifyPasswordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    //로그인
    @Transactional
    public String login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        User user = authRepository.findUserByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());

    }

    @Transactional
    public VerifyPasswordResponse verifyPassword(VerifyPasswordRequest request, CustomUserDetails userDetails) {
        User user = authRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new VerifyPasswordResponse(false);
        }

        return new VerifyPasswordResponse(true);
    }
}
