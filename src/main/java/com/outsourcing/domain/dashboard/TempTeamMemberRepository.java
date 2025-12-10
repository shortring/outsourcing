package com.outsourcing.domain.dashboard;

import com.outsourcing.common.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TempTeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("""
            SELECT tm2.userId.id FROM TeamMember tm1 JOIN TeamMember tm2
            ON tm1.teamId.id = tm2.teamId.id WHERE tm1.userId.id = : userId
            """)
    List<Long> findTeamMemberIdsByUserId(@Param("userId") Long userId);
}