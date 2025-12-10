package com.outsourcing.domain.teamMember.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetTeamDetailResponseDto {
    private final Long teamId;
    private final String name;
    private final String description;
    private final Instant createdAt;
    private final List<GetMemberDetailResponseDto> members;
}
