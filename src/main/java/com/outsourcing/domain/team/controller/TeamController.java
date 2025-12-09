package com.outsourcing.domain.team.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.team.dto.request.CreateTeamRequestDto;
import com.outsourcing.domain.team.dto.request.UpdateTeamRequestDto;
import com.outsourcing.domain.team.dto.response.CreateTeamResponseDto;
import com.outsourcing.domain.team.dto.response.GetDetailTeamResponseDto;
import com.outsourcing.domain.team.dto.response.UpdateTeamResponseDto;
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
    public ResponseEntity<ApiResponse<CreateTeamResponseDto>> createTeamApi (@RequestBody CreateTeamRequestDto requestDto) {
        CreateTeamResponseDto responseDto = teamService.createTeam(requestDto);


        ApiResponse<CreateTeamResponseDto> apiResponse = new ApiResponse<>(true, "팀이 생성되었습니다.", responseDto);

        ResponseEntity<ApiResponse<CreateTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        return response;
    }

    // 팀 상세 조회 ()
    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<GetDetailTeamResponseDto>> getDetailTeamApi (@PathVariable ("teamId") Long teamId) {
        GetDetailTeamResponseDto responseDto = teamService.getDetailTeam(teamId);

        ApiResponse<GetDetailTeamResponseDto> apiResponse = new ApiResponse<>(true, "팀 조회 성공", responseDto);

        ResponseEntity<ApiResponse<GetDetailTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<ApiResponse<UpdateTeamResponseDto>> updateTeamApi (
            @PathVariable ("teamId") Long teamId,
            @RequestBody UpdateTeamRequestDto requestDto) {

        UpdateTeamResponseDto responseDto = teamService.updateTeam(teamId, requestDto);

        ApiResponse<UpdateTeamResponseDto> apiResponse = new ApiResponse<>(true, "팀 정보가 수정되었습니다.", responseDto);

        ResponseEntity<ApiResponse<UpdateTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);

        return response;
    }


}
