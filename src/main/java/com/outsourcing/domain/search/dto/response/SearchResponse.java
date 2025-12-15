package com.outsourcing.domain.search.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SearchResponse {

    private final List<SearchTaskResponse> tasks;
    private final List<SearchTeamResponse> teams;
    private final List<SearchUserResponse> users;

    public static SearchResponse of(List<SearchTaskResponse> tasks, List<SearchTeamResponse> teams, List<SearchUserResponse> users) {

        return new SearchResponse(tasks, teams, users);
    }
}
