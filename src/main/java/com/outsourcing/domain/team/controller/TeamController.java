package com.outsourcing.domain.team.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.team.dto.request.CreateTeamRequestDto;
import com.outsourcing.domain.team.dto.request.UpdateTeamRequestDto;
import com.outsourcing.domain.team.dto.response.CreateTeamResponseDto;
import com.outsourcing.domain.team.dto.response.UpdateTeamResponseDto;
import com.outsourcing.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")

public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    public ResponseEntity<ApiResponse<CreateTeamResponseDto>> createTeamApi(@Valid @RequestBody CreateTeamRequestDto requestDto) {
        CreateTeamResponseDto responseDto = teamService.createTeam(requestDto);


        ApiResponse<CreateTeamResponseDto> apiResponse = ApiResponse.success("팀이 생성되었습니다.", responseDto);

        ResponseEntity<ApiResponse<CreateTeamResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
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
