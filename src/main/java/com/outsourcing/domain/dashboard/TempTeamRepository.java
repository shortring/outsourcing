package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempTeamRepository extends JpaRepository<Team, Long> {
}
