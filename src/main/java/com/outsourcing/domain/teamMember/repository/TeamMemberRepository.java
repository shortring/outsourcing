package com.outsourcing.domain.teamMember.repository;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("""
                select tm.user
                from TeamMember tm
                where tm.team.id = :teamId
            """)
    List<User> findUsersByTeamId(Long teamId);

    Optional<TeamMember> findByTeamIdAndUserId(Long teamId, Long userId);

    // 추가 시 이미 팀에 속한 유저인지 검증
    boolean existsByTeamAndUser(Team team, User user);

    boolean existsByTeam_IdAndUser_Id(Long teamId, Long userId);

    List<TeamMember> findAllByTeamId(Long teamId);

    @Query("""
            SELECT tm2.user.id FROM TeamMember tm1 JOIN TeamMember tm2
            ON tm1.team.id = tm2.team.id WHERE tm1.user.id = :userId
            """)
    Set<Long> findTeamMemberIdsByUserId(@Param("userId") Long userId);
}
