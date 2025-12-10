package com.outsourcing.domain.team.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateTeamRequestDto {
    private String name;
    private String description;
}
