package com.outsourcing.domain.team.repository;

import com.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("""
            SELECT t
            FROM Team t
            WHERE t.name LIKE %:query% OR t.description LIKE %:query%
            """)
    List<Team> searchByKeyword(@Param("query") String query);

    boolean existsByName(String name);

}
