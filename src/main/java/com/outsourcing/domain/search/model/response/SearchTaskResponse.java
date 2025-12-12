package com.outsourcing.domain.search.model.response;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchTaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;

    public static SearchTaskResponse from(Task task) {

        return new SearchTaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

}
