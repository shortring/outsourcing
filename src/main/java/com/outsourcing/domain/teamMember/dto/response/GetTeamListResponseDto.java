package com.outsourcing.domain.teamMember.dto.response;

import com.outsourcing.common.entity.Team;
import com.outsourcing.domain.team.dto.TeamDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetTeamListResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Instant createdAt;
    private final List<GetMemberListResponseDto> members;

}


