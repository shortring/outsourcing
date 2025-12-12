package com.outsourcing.domain.teamMember.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.IsDeleted;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.team.dto.TeamDto;
import com.outsourcing.domain.team.repository.TeamRepository;
import com.outsourcing.domain.team.service.TeamValidateService;
import com.outsourcing.domain.teamMember.dto.request.AddTeamMemberRequestDto;
import com.outsourcing.domain.teamMember.dto.response.*;
import com.outsourcing.domain.teamMember.mapper.TeamMemberMapper;
import com.outsourcing.domain.teamMember.repository.TeamMemberRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final TeamMemberMapper teamMemberMapper;
    private final TeamValidateService teamValidateService;

    // 멤버 추가
    @Transactional
    public AddTeamMemberResponseDto addMemberTeam(Long teamId, AddTeamMemberRequestDto request) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        User findUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        if (teamMemberRepository.existsByTeamAndUser(findTeam, findUser)) {
            throw new CustomException(ErrorMessage.CONFLICT_ALREADY_IN_TEAM);
        }

        TeamMember teamMember = new TeamMember(findTeam, findUser);

        teamMemberRepository.save(teamMember);

        AddTeamMemberResponseDto response = new AddTeamMemberResponseDto(
                findUser.getId(),
                findUser.getUsername(),
                findUser.getName()
        );
        return response;
    }

    // 멤버 제거
    @Transactional
    public void removeMemberTeam(Long teamId, Long pointUserId, Long userId) {
        teamValidateService.ValidateUser(teamId, userId, ErrorMessage.FORBIDDEN_NO_PERMISSION_UPDATE);

        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(teamId, pointUserId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM_MEMBER));

        teamMemberRepository.delete(teamMember);
    }

    // 팀 목록 조회
    @Transactional(readOnly = true)
    public List<GetTeamListResponseDto> getTeamList() {

        List<Team> teamList = teamRepository.findAll();
        List<GetTeamListResponseDto> response = new ArrayList<>();

        for (Team team : teamList) {

            List<TeamMember> members = teamMemberRepository.findAllByTeamIdFetchUser(team.getId());

            List<GetMemberListResponseDto> memberList = new ArrayList<>();
            for (TeamMember teamMember : members) {
                memberList.add(teamMemberMapper.getListDto(teamMember));
            }

            GetTeamListResponseDto responseDto = new GetTeamListResponseDto(
                    team.getId(),
                    team.getName(),
                    team.getDescription(),
                    team.getCreatedAt(),
                    memberList
            );
            response.add(responseDto);
        }
        return response;
    }

    // 팀 상세 조회
    @Transactional(readOnly = true)
    public GetTeamDetailResponseDto getTeamDetail(Long teamId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        List<TeamMember> members = teamMemberRepository.findAllByTeamIdFetchUser(team.getId());


        List<GetMemberDetailResponseDto> responseDto = new ArrayList<>();

        for (TeamMember teamMember : members) {

            responseDto.add(teamMemberMapper.getDetailDto(teamMember));
        }

        GetTeamDetailResponseDto response = new GetTeamDetailResponseDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                responseDto
        );
        return response;
    }

    // 팀 멤버 조회
    @Transactional(readOnly = true)
    public List<GetTeamMemberResponseDto> getTeamMember(Long teamId) {

        List<TeamMember> members = teamMemberRepository.findAllByTeamIdFetchUser(teamId);

        List<GetTeamMemberResponseDto> response = new ArrayList<>();

        for (TeamMember teamMember : members) {

            if (teamMember.getUser().getIsDeleted().equals(IsDeleted.FALSE)) {

                response.add(teamMemberMapper.getTeamMemberDto(teamMember));
            }
        }
        return response;
    }
}
