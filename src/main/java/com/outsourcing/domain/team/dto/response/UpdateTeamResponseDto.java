package com.outsourcing.domain.team.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class UpdateTeamResponseDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Instant createdAt;
}
