package com.outsourcing.domain.teamMember.repository;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByTeamIdAndUserId(Long teamId, Long userId);

    // 추가 시 이미 팀에 속한 유저인지 검증
    boolean existsByTeamAndUser(Team team, User user);

    boolean existsByTeam_IdAndUser_Id(Long teamId, Long userId);

    List<TeamMember> findAllByTeamId(Long teamId);
}
