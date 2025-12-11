package com.outsourcing.domain.auth.repository;

import com.outsourcing.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
