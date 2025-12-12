package com.outsourcing.domain.user.service;

import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.IsDeleted;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.user.model.UserDto;
import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.request.UpdateUserRequest;
import com.outsourcing.domain.user.model.response.AvailableUserResponse;
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

    //사용자 생성
    @Transactional
    public CreateUserResponse signup(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())){
            throw new CustomException(ErrorMessage.CONFLICT_EXIST_USERNAME);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorMessage.CONFLICT_EXIST_EMAIL);
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
            throw new CustomException(ErrorMessage.FORBIDDEN_NO_PERMISSION);
        }

        UserDto user = userRepository.findDtoById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        if(user.getIsDeleted()== IsDeleted.TRUE){
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }
        return GetUserResponse.from(user);
    }

    //사용자 전체 조회
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAllUser() {

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getIsDeleted() == IsDeleted.FALSE)
                .map(UserDto::from)
                .map(GetUserResponse::from)
                .toList();
    }

    //사용자 수정
    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request, CustomUserDetails userDetails) {

        if (!userId.equals(userDetails.getUserId())) {
            throw new CustomException(ErrorMessage.FORBIDDEN_NO_PERMISSION_UPDATE);
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        if (user.getIsDeleted().equals(IsDeleted.TRUE)){
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorMessage.CONFLICT_EXIST_EMAIL);
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
            throw new CustomException(ErrorMessage.FORBIDDEN_NO_PERMISSION);
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        if (user.getIsDeleted().equals(IsDeleted.TRUE)){
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        user.softDelete(IsDeleted.TRUE);
    }

    //추가 가능한 사용자 조회
    @Transactional(readOnly = true)
    public List<AvailableUserResponse> getAvailableUsers(Long teamId) {

        List<User> users = userRepository.findAvailableUsers(teamId);

        return users.stream()
                .filter(user -> user.getIsDeleted() == IsDeleted.FALSE)
                .map(UserDto::from)
                .map(AvailableUserResponse::from)
                .toList();

    }
}