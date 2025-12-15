package com.outsourcing.domain.search.service;

import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.search.dto.response.SearchResponse;
import com.outsourcing.domain.search.dto.response.SearchTaskResponse;
import com.outsourcing.domain.search.dto.response.SearchTeamResponse;
import com.outsourcing.domain.search.dto.response.SearchUserResponse;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.team.repository.TeamRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;

    // 톨합 검색
    @Transactional(readOnly = true)
    public SearchResponse search(String query) {

        if (query == null || query.isBlank()) {
            throw new CustomException(ErrorMessage.BAD_REQUEST_NOT_NULL_SEARCH_KEYWORD);
        }

        List<SearchTaskResponse> tasks = taskRepository.searchByKeyword(query)
                .stream()
                .map(SearchTaskResponse::from)
                .toList();

        List<SearchTeamResponse> teams = teamRepository.searchByKeyword(query)
                .stream()
                .map(SearchTeamResponse::from)
                .toList();

        List<SearchUserResponse> users = userRepository.searchByKeyword(query)
                .stream()
                .map(SearchUserResponse::from)
                .toList();

        return SearchResponse.of(tasks, teams, users);
    }
}

