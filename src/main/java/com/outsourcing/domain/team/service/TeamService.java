package com.outsourcing.domain.team.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.team.dto.request.CreateTeamRequest;
import com.outsourcing.domain.team.dto.request.UpdateTeamRequest;
import com.outsourcing.domain.team.dto.response.CreateTeamResponse;
import com.outsourcing.domain.team.dto.response.UpdateTeamResponse;
import com.outsourcing.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamValidateService teamValidateService;

    // 팀 생성
    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest requestDto) {
        if (teamRepository.existsByName(requestDto.getName())) {
            throw new CustomException(ErrorMessage.CONFLICT_EXIST_TEAM_NAME);
        }

        Team team = new Team(
                requestDto.getName(),
                requestDto.getDescription()
        );

        Team savedTeam = teamRepository.save(team);

        CreateTeamResponse response = new CreateTeamResponse(
                savedTeam.getId(),
                savedTeam.getName(),
                savedTeam.getDescription(),
                savedTeam.getCreatedAt()
        );
        return response;
    }

    // 팀 수정
    @Transactional
    public UpdateTeamResponse updateTeam(Long teamId, Long userId, UpdateTeamRequest requestDto) {
        teamValidateService.ValidateUser(teamId, userId, ErrorMessage.FORBIDDEN_NO_PERMISSION_UPDATE);

        Team findTeam = teamRepository.findById(teamId).orElseThrow
                (() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        findTeam.update(
                requestDto.getName(),
                requestDto.getDescription()
        );

        UpdateTeamResponse responseDto = new UpdateTeamResponse(
                findTeam.getId(),
                findTeam.getName(),
                findTeam.getDescription(),
                findTeam.getCreatedAt()
        );
        return responseDto;
    }

    // 팀 삭제
    @Transactional
    public void deleteTeam(Long id) {
        Team findTeam = teamRepository.findById(id).orElseThrow
                (() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        if (!findTeam.getMembers().isEmpty()) {
            throw new CustomException(ErrorMessage.CONFLICT_EXIST_MEMBER_IN_TEAM);
        }

        teamRepository.deleteById(findTeam.getId());
    }
}
