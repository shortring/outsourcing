package com.outsourcing.domain.teamMember.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.teamMember.dto.request.AddTeamMemberRequestDto;
import com.outsourcing.domain.teamMember.dto.response.AddTeamMemberResponseDto;
import com.outsourcing.domain.teamMember.dto.response.GetTeamDetailResponseDto;
import com.outsourcing.domain.teamMember.dto.response.GetTeamListResponseDto;
import com.outsourcing.domain.teamMember.dto.response.GetTeamMemberResponseDto;
import com.outsourcing.domain.teamMember.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    // 멤버 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<AddTeamMemberResponseDto>> addMemberApi(
            @PathVariable("teamId") Long teamId,
            @RequestBody AddTeamMemberRequestDto request
    ) {

        AddTeamMemberResponseDto responseDto = teamMemberService.addMemberTeam(teamId, request);

        ApiResponse<AddTeamMemberResponseDto> apiResponse = ApiResponse.success("팀 멤버가 추가되었습니다.", responseDto);

        ResponseEntity<ApiResponse<AddTeamMemberResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 멤버 제거
    @PostMapping("/{teamId}/members/{userId}")
    public ResponseEntity<ApiResponse<?>> removeMemberApi(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long userId
    ) {
        teamMemberService.removeMemberTeam(teamId, userId);

        ApiResponse<?> apiResponse = ApiResponse.success("팀 멤버가 제거되었습니다.", null);

        ResponseEntity<ApiResponse<?>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 팀 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetTeamListResponseDto>>> getTeamMemberListApi() {
        List<GetTeamListResponseDto> responseDto = teamMemberService.getTeamList();

        ApiResponse<List<GetTeamListResponseDto>> apiResponse = ApiResponse.success("팀 목록 조회 성공", responseDto);

        ResponseEntity<ApiResponse<List<GetTeamListResponseDto>>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 팀 상세 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<GetTeamDetailResponseDto>> getTeamDetailApi(@PathVariable ("teamId") Long teamId) {
        GetTeamDetailResponseDto responseDto = teamMemberService.getTeamDetail(teamId);

        ApiResponse<GetTeamDetailResponseDto> apiResponse = ApiResponse.success("팀 조회 성공", responseDto);

        ResponseEntity<ApiResponse<GetTeamDetailResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 팀 멤버 조회
    @GetMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<List<GetTeamMemberResponseDto>>> getTeamMemberApi(@PathVariable ("teamId") Long teamId) {
        List<GetTeamMemberResponseDto> responseDto = teamMemberService.getTeamMember(teamId);

        ApiResponse<List<GetTeamMemberResponseDto>> apiResponse = ApiResponse.success("팀 멤버 조회 성공", responseDto);

        ResponseEntity<ApiResponse<List<GetTeamMemberResponseDto>>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }
}
