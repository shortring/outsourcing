package com.outsourcing.domain.search.model.response;

import com.outsourcing.domain.search.model.response.SearchTaskResponse;
import com.outsourcing.domain.search.model.response.SearchTeamResponse;
import com.outsourcing.domain.search.model.response.SearchUserResponse;
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
