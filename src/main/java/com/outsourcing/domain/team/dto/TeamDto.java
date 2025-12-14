package com.outsourcing.domain.team.dto;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamDto {

    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private List<TeamMember> members;

    public static TeamDto from(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                team.getMembers()
        );
    }
}
