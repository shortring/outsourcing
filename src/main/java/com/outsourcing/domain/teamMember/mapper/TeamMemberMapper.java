package com.outsourcing.domain.teamMember.mapper;

import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.domain.teamMember.dto.response.GetMemberDetailResponse;
import com.outsourcing.domain.teamMember.dto.response.GetMemberListResponse;
import com.outsourcing.domain.teamMember.dto.response.GetTeamMemberResponse;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberMapper {

    public GetMemberListResponse getListDto(TeamMember teamMember) {
        return new GetMemberListResponse(
                teamMember.getUser().getId(),
                teamMember.getUser().getUsername(),
                teamMember.getUser().getName(),
                teamMember.getUser().getEmail(),
                teamMember.getUser().getRole(),
                teamMember.getUser().getCreatedAt()
        );
    }

    public GetMemberDetailResponse getDetailDto(TeamMember teamMember) {
        return new GetMemberDetailResponse(
                teamMember.getUser().getId(),
                teamMember.getUser().getUsername(),
                teamMember.getUser().getName(),
                teamMember.getUser().getEmail(),
                teamMember.getUser().getRole()
        );
    }

    public GetTeamMemberResponse getTeamMemberDto(TeamMember teamMember) {
        return new GetTeamMemberResponse(
                teamMember.getUser().getId(),
                teamMember.getUser().getUsername(),
                teamMember.getUser().getName(),
                teamMember.getUser().getEmail(),
                teamMember.getUser().getRole(),
                teamMember.getUser().getCreatedAt()
        );
    }
}

