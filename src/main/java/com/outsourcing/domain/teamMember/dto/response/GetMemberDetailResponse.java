package com.outsourcing.domain.teamMember.dto.response;

import com.outsourcing.common.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetMemberDetailResponse {
    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final UserRole role;
}
