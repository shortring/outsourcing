package com.outsourcing.domain.user.repository;

import com.outsourcing.common.entity.User;
import com.outsourcing.domain.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserDto> findDtoById(Long userId);

    @Query("""
            SELECT u
            FROM User u
            WHERE (:teamId IS NULL OR u.id NOT IN (
                SELECT tm.user.id FROM TeamMember tm WHERE tm.team.id = :teamId
            ))
            """)
    List<User> findAvailableUsers(@Param("teamId") Long teamId);

    @Query("""
           SELECT u
           FROM User u
           WHERE u.name LIKE %:query%
           """)
    List<User> searchByKeyword(@Param("query") String query);
}
