package com.outsourcing.domain.teamMember.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddTeamMemberResponseDto {

    private final Long id;
    private final String username;
    private final String name;
}
