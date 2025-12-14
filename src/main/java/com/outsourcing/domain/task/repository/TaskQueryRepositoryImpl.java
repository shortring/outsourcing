package com.outsourcing.domain.task.repository;

import com.outsourcing.common.entity.QUser;
import com.outsourcing.common.entity.task.QTask;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.DataStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskQueryRepositoryImpl implements TaskQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Task> search(Pageable pageable, TaskStatus status, String keyword, Long assigneeId) {

        QTask task = QTask.task;
        QUser assignee = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(task.dataStatus.eq(DataStatus.ACTIVE));

        if (keyword != null
                && !keyword.isBlank()) {
            builder.and(task.title.containsIgnoreCase(keyword)
                    .or(task.description.containsIgnoreCase(keyword)));
        }

        if (status != null) {
            builder.and(task.status.eq(status));
        }

        if (assigneeId != null) {
            builder.and(task.assignee.id.eq(assigneeId));
        }

        List<Task> tasks = jpaQueryFactory
                .selectFrom(task)
                .leftJoin(task.assignee, assignee).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(task.createdAt.desc())
                .fetch();

        return PageableExecutionUtils.getPage(
                tasks,
                pageable,
                () -> Optional.ofNullable(jpaQueryFactory
                        .select(task.count())
                        .from(task)
                        .where(builder)
                        .fetchOne()
                ).orElse(0L));
    }
}
