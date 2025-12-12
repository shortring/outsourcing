package com.outsourcing.domain.search.model.response;

import com.outsourcing.common.entity.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchTeamResponse {

    private final Long id;
    private final String name;
    private final String description;

    public static SearchTeamResponse from(Team team) {

        return new SearchTeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription()
        );
    }
}
