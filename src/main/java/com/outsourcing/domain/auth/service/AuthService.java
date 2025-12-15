package com.outsourcing.domain.auth.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.IsDeleted;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.common.utils.JwtUtil;
import com.outsourcing.domain.auth.dto.request.LoginRequest;
import com.outsourcing.domain.auth.dto.request.VerifyPasswordRequest;
import com.outsourcing.domain.auth.dto.response.VerifyPasswordResponse;
import com.outsourcing.domain.auth.repository.AuthRepository;
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
                () -> new CustomException(ErrorMessage.UNAUTHORIZED_WRONG_ID_PASSWORD)
        );

        if (user.getIsDeleted().equals(IsDeleted.TRUE)) {
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorMessage.UNAUTHORIZED_WRONG_PASSWORD);
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());

    }

    //비밀번호 검증
    @Transactional
    public VerifyPasswordResponse verifyPassword(VerifyPasswordRequest request, CustomUserDetails userDetails) {

        User user = authRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new CustomException(ErrorMessage.UNAUTHORIZED_WRONG_ID_PASSWORD)
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            return new VerifyPasswordResponse(false);
        }

        return new VerifyPasswordResponse(true);
    }
}
