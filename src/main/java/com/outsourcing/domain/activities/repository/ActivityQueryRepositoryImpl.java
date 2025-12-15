package com.outsourcing.domain.activities.repository;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.QActivity;
import com.outsourcing.common.entity.QUser;
import com.outsourcing.common.entity.task.QTask;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ActivityQueryRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Activity> search(ActivityType type, Long taskId, Instant from, Instant to, Pageable pageable) {
        BooleanBuilder where = buildWhere(null, type, taskId, from, to);

        return executeSearch(where, pageable);
    }

    public Page<Activity> search(Long userId, ActivityType type, Long taskId, Instant from, Instant to, Pageable pageable) {
        BooleanBuilder where = buildWhere(userId, type, taskId, from, to);
        return executeSearch(where, pageable);
    }

    public BooleanBuilder buildWhere(
            Long userId,
            ActivityType type,
            Long taskId,
            Instant from,
            Instant to
    ) {

        QActivity activity = QActivity.activity;

        BooleanBuilder where = new BooleanBuilder();

        if (userId != null) {
            where.and(activity.user.id.eq(userId));
        }

        if (type != null) {
            where.and(activity.type.eq(type));
        }

        if (taskId != null) {
            where.and(activity.task.id.eq(taskId));
        }

        if (from != null && to != null) {
            where.and(activity.timestamp.between(from, to));
        } else if (from != null) {
            where.and(activity.timestamp.goe(from));
        } else if (to != null) {
            where.and(activity.timestamp.lt(to));
        }

        return where;
    }

    private Page<Activity> executeSearch(BooleanBuilder where, Pageable pageable) {

        QActivity activity = QActivity.activity;
        QUser user = QUser.user;
        QTask task = QTask.task;

        List<Activity> contents = jpaQueryFactory
                .selectFrom(activity)
                .leftJoin(activity.user, user).fetchJoin()
                .leftJoin(activity.task, task).fetchJoin()
                .where(where)
                .orderBy(activity.timestamp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(
                contents,
                pageable,
                () -> Optional.ofNullable(
                        jpaQueryFactory
                                .select(activity.count())
                                .from(activity)
                                .where(where)
                                .fetchOne()
                ).orElse(0L)
        );
    }
}
