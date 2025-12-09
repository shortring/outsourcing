package com.outsourcing.domain.user.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.domain.user.model.UserDto;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public CreateUserResponse signup(CreateUserRequest request) {
        User user = new User(request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getRole()
        );

        userRepository.save(user);

        UserDto dto = UserDto.from(user);

        return CreateUserResponse.from(dto);
    }

}