package com.outsourcing.domain.team.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.team.dto.request.CreateTeamRequestDto;
import com.outsourcing.domain.team.dto.request.UpdateTeamRequestDto;
import com.outsourcing.domain.team.dto.response.CreateTeamResponseDto;
import com.outsourcing.domain.team.dto.response.GetDetailTeamResponseDto;
import com.outsourcing.domain.team.dto.response.UpdateTeamResponseDto;
import com.outsourcing.domain.team.repository.TeamRepository;
import com.outsourcing.domain.teamMember.dto.request.TeamAddMemberRequest;
import com.outsourcing.domain.teamMember.dto.response.TeamAddMemberResponse;
import com.outsourcing.domain.teamMember.repository.TeamMemberRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    // 팀 생성
    @Transactional
    public CreateTeamResponseDto createTeam(CreateTeamRequestDto requestDto) {
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

    // 멤버 추가
    @Transactional
    public List<TeamAddMemberResponse> addMemberTeam(Long teamId, TeamAddMemberRequest request) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        User findUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        if (teamMemberRepository.existsByTeamAndUser(findTeam, findUser)) {
            throw new CustomException(ErrorMessage.CONFLICT_ALREADY_IN_TEAM);
        }

        TeamMember teamMember = new TeamMember(findTeam, findUser);

        teamMemberRepository.save(teamMember);

        List<TeamAddMemberResponse> response = findTeam.getMembers().stream()
                .map(teamMembers -> new TeamAddMemberResponse(
                        teamMembers.getUser().getId(),
                        teamMembers.getUser().getUsername(),
                        teamMembers.getUser().getName()
                ))
                .toList();

        return response;
    }

    // 멤버 삭제
    @Transactional
    public void removeMemberTeam(Long teamId, Long userId) {
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM_MEMBER));

        teamMemberRepository.delete(teamMember);
    }

    // 팀 상세 조회 (팀의 정보와 팀에 속한 멤버 조회)
    @Transactional
    public GetDetailTeamResponseDto getDetailTeam(Long teamId) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));
        GetDetailTeamResponseDto responseDto = new GetDetailTeamResponseDto(
                findTeam.getId(),
                findTeam.getName(),
                findTeam.getDescription(),
                findTeam.getCreatedAt()
        );
        return responseDto;
    }

    // 팀 수정
    @Transactional
    public UpdateTeamResponseDto updateTeam(Long id, UpdateTeamRequestDto requestDto) {
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
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }


}
