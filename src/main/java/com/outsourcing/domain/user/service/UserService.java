package com.outsourcing.domain.user.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.utils.JwtUtil;
import com.outsourcing.domain.user.model.UserDto;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.request.LoginRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.model.response.GetUserResponse;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public CreateUserResponse signup(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getRole()
        );

        userRepository.save(user);

        UserDto dto = UserDto.from(user);

        return CreateUserResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId) {

        UserDto user = userRepository.findDtoById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        return GetUserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAllUser(Long userId) {

        List<UserDto> user = userRepository.findAllBy(userId);

        return user.stream()
                .map(GetUserResponse::from)
                .toList();
    }

    @Transactional
    public String login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());

    }
}