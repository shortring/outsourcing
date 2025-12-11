package com.outsourcing.domain.task.repository;

import com.outsourcing.common.entity.QUser;
import com.outsourcing.common.entity.task.QTask;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

        // 1. Q클래스 선언.
        QTask task = QTask.task;
        QUser assignee =  QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        // 2. 키워드
        if(keyword!=null
                && !keyword.isBlank()) {
            builder.and(task.title.containsIgnoreCase(keyword)
                    .or(task.description.containsIgnoreCase(keyword)));
        }

        // 3. 상태
        if(status!=null){
            builder.and(task.status.eq(status));
        }

        // 5. 담당자 : assignedId
        if(assigneeId!=null){
            builder.and(task.assignee.id.eq(assigneeId));
        }


        // 6. 리스트
        List<Task> tasks=jpaQueryFactory
                .selectFrom(task)
                .leftJoin(task.assignee,assignee).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(task.createdAt.desc())
                .fetch();

        // 7. 합계.
//        Long total=jpaQueryFactory
//                .select(task.count())
//                .from(task)
//                .where(builder)
//                .fetchOne(); // Long
//
//        // 8. Page
//        return new PageImpl<>(tasks,
//                pageable,
//                Optional.ofNullable(total)
//                        .orElse(0L)
//        );

        return PageableExecutionUtils.getPage(
                tasks,
                pageable,
                ()->Optional.ofNullable(jpaQueryFactory
                        .select(task.count())
                        .from(task)
                        .where(builder)
                        .fetchOne()
                ).orElse(0L));

    }
}
