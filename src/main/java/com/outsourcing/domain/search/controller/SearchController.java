package com.outsourcing.domain.search.controller;

import com.outsourcing.common.dto.ApiResponse;
import com.outsourcing.domain.search.model.response.SearchResponse;
import com.outsourcing.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService tempService;

    @GetMapping("/api/search")
    public ResponseEntity<ApiResponse<SearchResponse>> searchApi(@RequestParam(value = "query") String query) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("검색 성공", tempService.search(query)));
    }
}
