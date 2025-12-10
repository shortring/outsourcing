package com.outsourcing.domain.team.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.domain.team.dto.request.CreateTeamRequestDto;
import com.outsourcing.domain.team.dto.request.UpdateTeamRequestDto;
import com.outsourcing.domain.team.dto.response.CreateTeamResponseDto;
import com.outsourcing.domain.team.dto.response.UpdateTeamResponseDto;
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

    // 팀 수정
    @Transactional
    public UpdateTeamResponseDto updateTeam (Long id, UpdateTeamRequestDto requestDto) {
        Team findTeam = teamRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("존재하지 않는 팀입니다.")
        );

        findTeam.update(
                requestDto.getName(),
                requestDto.getDescription()
        );

        UpdateTeamResponseDto responseDto = new UpdateTeamResponseDto(
                findTeam.getId(),
                findTeam.getName(),
                findTeam.getDescription(),
                findTeam.getCreatedAt()
        );
        return responseDto;
    }

    // 팀 삭제
    @Transactional
    public void deleteTeam (Long id) {
        teamRepository.deleteById(id);
    }
}
