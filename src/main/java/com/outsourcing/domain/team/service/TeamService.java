package com.outsourcing.domain.team.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.domain.team.dto.request.TeamCreateRequestDto;
import com.outsourcing.domain.team.dto.response.TeamCreateResponseDto;
import com.outsourcing.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public TeamCreateResponseDto createTeam (TeamCreateRequestDto requestDto) {
        Team team = new Team(
                requestDto.getName(),
                requestDto.getDescription()
        );

        Team savedTeam = teamRepository.save(team);

        TeamCreateResponseDto response = new TeamCreateResponseDto(
                savedTeam.getId(),
                savedTeam.getName(),
                savedTeam.getDescription(),
                savedTeam.getCreatedAt()
        );
        return response;
    }

}
