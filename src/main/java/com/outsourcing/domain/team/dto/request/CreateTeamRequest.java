package com.outsourcing.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateTeamRequest {

    @NotBlank(message = "팀 이름은 필수입니다.")
    private String name;

    private String description;
}
