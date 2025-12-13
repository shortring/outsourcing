package com.outsourcing.domain.activities.repository;


import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.QActivity;
import com.outsourcing.common.entity.QUser;
import com.outsourcing.common.entity.task.QTask;
import com.outsourcing.common.enums.DataStatus;
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


    public Page<Activity> search(ActivityType type, Long taskId, Instant from, Instant to, Pageable pageable){
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

        // 0. 기본 필터 : 삭제 확정의 경우, DELETED로. : 보충 필요.

        // 1. userId
        if(userId!=null){
            where.and(activity.user.id.eq(userId));
        }

        // 1. type
        if (type != null) {
            where.and(activity.type.eq(type));
        }

        // 2. task
        if(taskId!=null){
            where.and(activity.task.id.eq(taskId));
        }

        // 3. from to
        if (from != null && to != null) {
            where.and(activity.timestamp.between(from, to));
        } // 3-1). to가 비어있는 경우
        else if (from != null) { // goe >= / gt >
            where.and(activity.timestamp.goe(from));
        } // 3-2. from이 비어있는 경우
        else if (to != null) { // loe <= / lt <   이미 존재하는 활동 기록 > = to
            where.and(activity.timestamp.lt(to));
        }

        // -- 여기까지가 검색 조건 -- //
        // 입력 : 활동 타입, 작업 ID, 언제부터, 언제까지
        // 출력 : 시간, 사용자, 활동 타입, 작업 ID, 변경내용

        return where;
    }


    private Page<Activity> executeSearch(
                                         BooleanBuilder where,
                                         Pageable pageable) {
        QActivity activity = QActivity.activity;
        QUser user = QUser.user;
        QTask task = QTask.task;

        List<Activity> contents = jpaQueryFactory
                .selectFrom(activity)
                .leftJoin(activity.user, user).fetchJoin()
                .leftJoin(activity.task, task).fetchJoin()
                .where(where)
                .orderBy(activity.timestamp.desc())
                .offset(pageable.getOffset()) // page*size  /size : 한페이지에 몇개 들어갈지, offset : 해당 페이지 시작점, limit : 해당 페이지 끝지점
                .limit(pageable.getPageSize()) // page : 바구니 size :
                .fetch();

        return PageableExecutionUtils.getPage(
                contents,
                pageable,
                ()-> Optional.ofNullable(
                        jpaQueryFactory
                                .select(activity.count())
                                .from(activity)
                                .where(where)
                                .fetchOne() // long
                ).orElse(0L)
        );
    }
}
