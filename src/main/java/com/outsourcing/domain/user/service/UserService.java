package com.outsourcing.domain.user.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.utils.JwtUtil;
import com.outsourcing.domain.user.model.UserDto;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.request.LoginRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public CreateUserResponse signup(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
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


    public String login(LoginRequest request){
        String username =request.getUsername();
        String password = request.getPassword();

        User user =userRepository.findUserByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());

    }
}