package com.outsourcing.domain.teamMember.mapper;

import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.domain.teamMember.dto.response.GetMemberDetailResponseDto;
import com.outsourcing.domain.teamMember.dto.response.GetMemberListResponseDto;
import com.outsourcing.domain.teamMember.dto.response.GetTeamMemberResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberMapper {

    public GetMemberListResponseDto getListDto(TeamMember teamMember) {
        return new GetMemberListResponseDto(
                teamMember.getUser().getId(),
                teamMember.getUser().getUsername(),
                teamMember.getUser().getName(),
                teamMember.getUser().getEmail(),
                teamMember.getUser().getRole(),
                teamMember.getUser().getCreatedAt()
        );
    }

    public GetMemberDetailResponseDto getDetailDto(TeamMember teamMember) {
        return new GetMemberDetailResponseDto(
                teamMember.getUser().getId(),
                teamMember.getUser().getUsername(),
                teamMember.getUser().getName(),
                teamMember.getUser().getEmail(),
                teamMember.getUser().getRole()
        );
    }

    public GetTeamMemberResponseDto getTeamMemberDto(TeamMember teamMember) {
        return new GetTeamMemberResponseDto(
                teamMember.getUser().getId(),
                teamMember.getUser().getUsername(),
                teamMember.getUser().getName(),
                teamMember.getUser().getEmail(),
                teamMember.getUser().getRole(),
                teamMember.getUser().getCreatedAt()
        );
    }
}

