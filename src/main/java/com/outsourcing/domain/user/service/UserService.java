package com.outsourcing.domain.user.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.common.utils.JwtUtil;
import com.outsourcing.domain.user.model.UserDto;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.request.UpdateUserRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.model.response.GetUserResponse;
import com.outsourcing.domain.user.model.response.UpdateUserResponse;
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

    //사용자 생성
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

    //프로필 조회
    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId, CustomUserDetails userDetails) {

        if (!userId.equals(userDetails.getUserId())) {
            throw new IllegalArgumentException("다른 사용자의 정보는 조회할 수 없습니다.");
        }

        UserDto user = userRepository.findDtoById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        return GetUserResponse.from(user);
    }

    //사용자 전체 조회
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAllUser() {

        return userRepository.findAll()
                .stream()
                .map(UserDto::from)
                .map(GetUserResponse::from)
                .toList();
    }

    //사용자 수정
    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request, CustomUserDetails userDetails) {

        if (!userId.equals(userDetails.getUserId())) {
            throw new IllegalArgumentException("본인만 수정 가능합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        user.modify(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        UserDto dto = UserDto.from(user);

        return UpdateUserResponse.from(dto);
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(Long userId, CustomUserDetails userDetails) {

        if (!userId.equals(userDetails.getUserId())) {
            throw new IllegalArgumentException("본인만 탈퇴 가능합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        userRepository.delete(user);
    }

}