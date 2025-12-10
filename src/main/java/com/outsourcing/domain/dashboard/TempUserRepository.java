package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<User, Long> {

}
