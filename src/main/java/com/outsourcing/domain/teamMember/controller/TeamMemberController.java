package com.outsourcing.domain.teamMember.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.common.filter.CustomUserDetails;
import com.outsourcing.domain.teamMember.dto.request.AddTeamMemberRequest;
import com.outsourcing.domain.teamMember.dto.response.AddTeamResponse;
import com.outsourcing.domain.teamMember.dto.response.GetTeamDetailResponse;
import com.outsourcing.domain.teamMember.dto.response.GetTeamListResponse;
import com.outsourcing.domain.teamMember.dto.response.GetTeamMemberResponse;
import com.outsourcing.domain.teamMember.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    // 멤버 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<AddTeamResponse>> addMemberApi(
            @PathVariable("teamId") Long teamId,
            @RequestBody AddTeamMemberRequest request
    ) {

        AddTeamResponse responseDto = teamMemberService.addMemberTeam(teamId, request);

        ApiResponse<AddTeamResponse> apiResponse = ApiResponse.success("팀 멤버가 추가되었습니다.", responseDto);

        ResponseEntity<ApiResponse<AddTeamResponse>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 멤버 제거
    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<ApiResponse<?>> removeMemberApi(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long pointUserId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();

        teamMemberService.removeMemberTeam(teamId, pointUserId, userId);

        ApiResponse<?> apiResponse = ApiResponse.success("팀 멤버가 제거되었습니다.", null);

        ResponseEntity<ApiResponse<?>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 팀 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetTeamListResponse>>> getTeamMemberListApi() {
        List<GetTeamListResponse> responseDto = teamMemberService.getTeamList();

        ApiResponse<List<GetTeamListResponse>> apiResponse = ApiResponse.success("팀 목록 조회 성공", responseDto);

        ResponseEntity<ApiResponse<List<GetTeamListResponse>>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 팀 상세 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<GetTeamDetailResponse>> getTeamDetailApi(@PathVariable("teamId") Long teamId) {
        GetTeamDetailResponse responseDto = teamMemberService.getTeamDetail(teamId);

        ApiResponse<GetTeamDetailResponse> apiResponse = ApiResponse.success("팀 조회 성공", responseDto);

        ResponseEntity<ApiResponse<GetTeamDetailResponse>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    // 팀 멤버 조회
    @GetMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<List<GetTeamMemberResponse>>> getTeamMemberApi(@PathVariable("teamId") Long teamId) {
        List<GetTeamMemberResponse> responseDto = teamMemberService.getTeamMember(teamId);

        ApiResponse<List<GetTeamMemberResponse>> apiResponse = ApiResponse.success("팀 멤버 조회 성공", responseDto);

        ResponseEntity<ApiResponse<List<GetTeamMemberResponse>>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }
}
