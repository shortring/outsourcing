package com.outsourcing.domain.team.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class TeamCreateResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Instant createdAt;
}
