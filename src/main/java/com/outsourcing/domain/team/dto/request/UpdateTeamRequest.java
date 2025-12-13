package com.outsourcing.domain.team.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateTeamRequest {

    private String name;
    private String description;
}
