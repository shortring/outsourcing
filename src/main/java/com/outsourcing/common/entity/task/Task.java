package com.outsourcing.common.entity.task;

import com.outsourcing.common.entity.BaseTimeEntity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.enums.DataStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@Table(name = "tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="task_status", nullable=false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name="task_priority", nullable=false)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name="data_status", nullable=false)
    private DataStatus dataStatus=DataStatus.ACTIVE;

    @Column(name="archived_at")
    private Instant archivedAt;

    // 작성자 권한 근거
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by_id", nullable = false)
//    private User createdBy;

    // 담당자 : JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;


    // 팀
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id", nullable = false)
//    private Team team;

    @Column
    private Instant dueDate;


    // 편의 메서드 : FE/DTO : assigneeId
    public Long getAssigneeId() {
        return assignee != null
                ? assignee.getId()
                : null;
    }

    public Task(String title, String description, TaskPriority priority,
                User assignee, Instant dueDate) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.priority = (priority != null)
                ? priority
                : TaskPriority.HIGH;
        this.assignee = assignee;
        this.dueDate = dueDate == null
                ? Instant.now()
                : dueDate;
    }

    public void update(String title, String description, TaskPriority priority, User assignee, Instant dueDate, Instant now) {
        this.title = title;
        this.description = description;
        this.priority = (priority != null)
                ? priority
                : TaskPriority.HIGH;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.updatedAt = now;
    }

    // 스테이터스 변경.
    public void changeStatus(TaskStatus status) {
        this.status = (status != null)
                ? status
                : TaskStatus.TODO;
    }

    public void isArchived(){
        if(this.dataStatus == DataStatus.ARCHIVED){ return; } // 멱등
        this.dataStatus=DataStatus.ARCHIVED;
        this.archivedAt=Instant.now();
    }
}
