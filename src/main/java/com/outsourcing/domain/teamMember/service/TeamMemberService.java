package com.outsourcing.domain.teamMember.service;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.IsDeleted;
import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.team.repository.TeamRepository;
import com.outsourcing.domain.team.service.TeamValidateService;
import com.outsourcing.domain.teamMember.dto.request.AddTeamMemberRequest;
import com.outsourcing.domain.teamMember.dto.response.*;
import com.outsourcing.domain.teamMember.mapper.TeamMemberMapper;
import com.outsourcing.domain.teamMember.repository.TeamMemberRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public AddTeamResponse addMemberTeam(Long teamId, AddTeamMemberRequest request) {

        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        User findUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        if (teamMemberRepository.existsByTeamAndUser(findTeam, findUser)) {
            throw new CustomException(ErrorMessage.CONFLICT_ALREADY_IN_TEAM);
        }

        TeamMember teamMember = new TeamMember(findTeam, findUser);
        teamMemberRepository.save(teamMember);

        List<User> members = teamMemberRepository.findUsersByTeamId(teamId);

        List<AddTeamMemberResponse> memberResponse = new ArrayList<>();
        for (User user : members) {
            AddTeamMemberResponse membersDto = new AddTeamMemberResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole(),
                    user.getCreatedAt()
            );
            memberResponse.add(membersDto);
        }

        AddTeamResponse response = new AddTeamResponse(
                findTeam.getId(),
                findTeam.getName(),
                findTeam.getDescription(),
                findTeam.getCreatedAt(),
                memberResponse
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
    public List<GetTeamListResponse> getTeamList() {

        List<Team> teamList = teamRepository.findAll();
        List<GetTeamListResponse> response = new ArrayList<>();

        for (Team team : teamList) {

            List<TeamMember> members = teamMemberRepository.findAllByTeamIdFetchUser(team.getId());

            List<GetMemberListResponse> memberList = new ArrayList<>();
            for (TeamMember teamMember : members) {
                memberList.add(teamMemberMapper.getListDto(teamMember));
            }

            GetTeamListResponse responseDto = new GetTeamListResponse(
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
    public GetTeamDetailResponse getTeamDetail(Long teamId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_TEAM));

        List<TeamMember> members = teamMemberRepository.findAllByTeamIdFetchUser(team.getId());


        List<GetMemberDetailResponse> responseDto = new ArrayList<>();

        for (TeamMember teamMember : members) {

            responseDto.add(teamMemberMapper.getDetailDto(teamMember));
        }

        GetTeamDetailResponse response = new GetTeamDetailResponse(
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
    public List<GetTeamMemberResponse> getTeamMember(Long teamId) {

        List<TeamMember> members = teamMemberRepository.findAllByTeamIdFetchUser(teamId);

        List<GetTeamMemberResponse> response = new ArrayList<>();

        for (TeamMember teamMember : members) {

            if (teamMember.getUser().getIsDeleted().equals(IsDeleted.FALSE)) {

                response.add(teamMemberMapper.getTeamMemberDto(teamMember));
            }
        }

        return response;
    }
}
