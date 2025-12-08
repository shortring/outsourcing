package com.outsourcing.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="tasks")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

//    @Enumerated(EnumType.STRING)
//    private TaskStatus status;
//
//    @Enumerated(EnumType.STRING)
//    private TaskPriority priority;

    // 작성자 권한 근거
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    // 담당자 : JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;


    // 팀
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id", nullable = false)
//    private Team team;

    @Column
    private LocalDateTime dueDate;


//    // 편의 메서드 : FE/DTO : assigneeId
//    public Long getAssigneeId() {
//        return assignee != null
//                ? assignee.getId()
//                : null;
//    }
//
//    @Builder(access= AccessLevel.PRIVATE)
//    private Task(
//            User assignee,
//            String title,
//            String description,
//            TaskStatus status,
//            TaskPriority priority
//    ) {
//        this.assignee = assignee;
//        this.title = title;
//        this.description = description;
//        this.status = status;
//        this.priority = priority;
//    }
//
//    public static Task of(
//            User assignee,
//            String title,
//            String description,
//            TaskPriority priority
//    ) {
//        return Task.builder()
//                .assignee(assignee)
//                .title(title)
//                .description(description)
//                .status(TaskStatus.TODO)
//                .priority(priority)
//                .build();
//    }
}
