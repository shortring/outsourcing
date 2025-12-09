package com.outsourcing.domain.team.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.team.dto.request.TeamCreateRequestDto;
import com.outsourcing.domain.team.dto.response.TeamCreateResponseDto;
import com.outsourcing.domain.team.dto.response.TeamGetDetailResponseDto;
import com.outsourcing.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/teams")

public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    public ResponseEntity<ApiResponse<TeamCreateResponseDto>> createTeamApi (@RequestBody TeamCreateRequestDto requestDto) {
        TeamCreateResponseDto responseDto = teamService.createTeam(requestDto);


        ApiResponse<TeamCreateResponseDto> apiResponse = new ApiResponse<>(true, "팀이 생성되었습니다.", responseDto);

        ResponseEntity<ApiResponse<TeamCreateResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        return response;
    }

    // 팀 상세 조회 ()
    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<TeamGetDetailResponseDto>> getDetailTeamApi (@PathVariable ("teamId") Long teamId) {
        TeamGetDetailResponseDto responseDto = teamService.getDetailTeam(teamId);

        ApiResponse<TeamGetDetailResponseDto> apiResponse = new ApiResponse<>(true, "팀 조회 성공", responseDto);

        ResponseEntity<ApiResponse<TeamGetDetailResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }
}
