package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TempTeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("""
            SELECT tm2.user.id FROM TeamMember tm1 JOIN TeamMember tm2
            ON tm1.team.id = tm2.team.id WHERE tm1.user.id = : userId
            """)
    Set<Long> findTeamMemberIdsByUserId(@Param("userId") Long userId);
}