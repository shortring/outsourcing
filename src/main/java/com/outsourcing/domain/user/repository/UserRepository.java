package com.outsourcing.domain.user.repository;

import com.outsourcing.common.entity.User;
import com.outsourcing.domain.user.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<UserDto> findDtoById(Long userId);

    List<User> findAll( );
}
