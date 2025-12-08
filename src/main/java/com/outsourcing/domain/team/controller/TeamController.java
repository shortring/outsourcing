package com.outsourcing.domain.team.controller;

import com.outsourcing.domain.team.dto.request.TeamCreateRequestDto;
import com.outsourcing.domain.team.dto.response.TeamCreateResponseDto;
import com.outsourcing.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/teams")

public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public TeamCreateResponseDto createTeamApi (@RequestBody TeamCreateRequestDto requestDto) {
        TeamCreateResponseDto responseDto = teamService.createTeam(requestDto);
        return responseDto;
    }
}
