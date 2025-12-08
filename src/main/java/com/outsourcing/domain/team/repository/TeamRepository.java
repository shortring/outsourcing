package com.outsourcing.domain.team.repository;

import com.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
