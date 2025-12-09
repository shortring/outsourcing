package com.outsourcing.domain.team.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.domain.team.dto.request.CreateTeamRequestDto;
import com.outsourcing.domain.team.dto.response.CreateTeamResponseDto;
import com.outsourcing.domain.team.dto.response.GetDetailTeamResponseDto;
import com.outsourcing.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // 팀 생성
    @Transactional
    public CreateTeamResponseDto createTeam (CreateTeamRequestDto requestDto) {
        Team team = new Team(
                requestDto.getName(),
                requestDto.getDescription()
        );

        Team savedTeam = teamRepository.save(team);

        CreateTeamResponseDto response = new CreateTeamResponseDto(
                savedTeam.getId(),
                savedTeam.getName(),
                savedTeam.getDescription(),
                savedTeam.getCreatedAt()
        );
        return response;
    }

    // 팀 상세 조회 (팀의 정보와 팀에 속한 멤버 조회)
    @Transactional
    public GetDetailTeamResponseDto getDetailTeam (Long teamId) {
        Team findTeam = teamRepository.findById(teamId).orElseThrow
                ( () -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        GetDetailTeamResponseDto responseDto = new GetDetailTeamResponseDto(
                findTeam.getId(),
                findTeam.getName(),
                findTeam.getDescription(),
                findTeam.getCreatedAt()
        );

        return responseDto;
    }



}
