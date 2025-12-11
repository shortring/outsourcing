package com.outsourcing.domain.team.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.team.dto.request.CreateTeamRequestDto;
import com.outsourcing.domain.team.dto.request.UpdateTeamRequestDto;
import com.outsourcing.domain.team.dto.response.CreateTeamResponseDto;
import com.outsourcing.domain.team.dto.response.GetDetailTeamResponseDto;
import com.outsourcing.domain.team.dto.response.UpdateTeamResponseDto;
import com.outsourcing.domain.team.service.TeamService;
import com.outsourcing.domain.teamMember.dto.request.TeamAddMemberRequest;
import com.outsourcing.domain.teamMember.dto.response.TeamAddMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")

public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    public ResponseEntity<ApiResponse<CreateTeamResponseDto>> createTeamApi(@RequestBody CreateTeamRequestDto requestDto) {
        CreateTeamResponseDto responseDto = teamService.createTeam(requestDto);

        ApiResponse<CreateTeamResponseDto> apiResponse = ApiResponse.success("팀이 생성되었습니다.", responseDto);

        ResponseEntity<ApiResponse<CreateTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        return response;
    }

    // 멤버 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<List<TeamAddMemberResponse>>> addMemberApi(
            @PathVariable("teamId") Long teamId,
            @RequestBody TeamAddMemberRequest request
    ) {

        List<TeamAddMemberResponse> responseDto = teamService.addMemberTeam(teamId, request);

        ApiResponse<List<TeamAddMemberResponse>> apiResponse = ApiResponse.success("팀 멤버가 추가되었습니다.", responseDto);

        ResponseEntity<ApiResponse<List<TeamAddMemberResponse>>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;

    }

    // 멤버 삭제
    @PostMapping("/{teamId}/members/{userId}")
    public ResponseEntity<ApiResponse<?>> removeMemberApi(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long userId
    ) {
        teamService.removeMemberTeam(teamId, userId);

        ApiResponse<?> apiResponse = ApiResponse.success("팀 멤버가 제거되었습니다.", null);

        ResponseEntity<ApiResponse<?>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }


    // 팀 상세 조회 ()
    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<GetDetailTeamResponseDto>> getDetailTeamApi(@PathVariable("teamId") Long teamId) {
        GetDetailTeamResponseDto responseDto = teamService.getDetailTeam(teamId);

        ApiResponse<GetDetailTeamResponseDto> apiResponse = ApiResponse.success("팀 조회 성공", responseDto);

        ResponseEntity<ApiResponse<GetDetailTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<ApiResponse<UpdateTeamResponseDto>> updateTeamApi(
            @PathVariable("teamId") Long teamId,
            @RequestBody UpdateTeamRequestDto requestDto) {

        UpdateTeamResponseDto responseDto = teamService.updateTeam(teamId, requestDto);

        ApiResponse<UpdateTeamResponseDto> apiResponse = ApiResponse.success("팀 정보가 수정되었습니다.", responseDto);

        ResponseEntity<ApiResponse<UpdateTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<ApiResponse<Void>> deleteTeamApi(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);

        ApiResponse<Void> apiResponse = ApiResponse.success("팀이 삭제되었습니다.", null);

        ResponseEntity<ApiResponse<Void>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }


}
