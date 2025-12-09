package com.outsourcing.domain.dashboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryTaskResponse {
    private final Long id;
    private final String title;
    private final String status;
    private final String priority;
    private final String dueDate;
}
