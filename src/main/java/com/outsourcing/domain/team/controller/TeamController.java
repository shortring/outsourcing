package com.outsourcing.domain.team.controller;

import com.outsourcing.domain.team.dto.request.TeamCreateRequestDto;
import com.outsourcing.domain.team.dto.response.TeamCreateResponseDto;
import com.outsourcing.domain.team.dto.response.TeamGetDetailResponseDto;
import com.outsourcing.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/teams")

public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    public TeamCreateResponseDto createTeamApi (@RequestBody TeamCreateRequestDto requestDto) {
        TeamCreateResponseDto responseDto = teamService.createTeam(requestDto);
        return responseDto;
    }

    // 팀 상세 조회 ()
    @GetMapping("/{teamId}")
    public TeamGetDetailResponseDto getDetailTeamApi (@PathVariable ("teamId") Long teamId) {
        TeamGetDetailResponseDto responseDto = teamService.getDetailTeam(teamId);

        return responseDto;
    }
}
