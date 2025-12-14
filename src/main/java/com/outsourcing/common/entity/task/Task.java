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
    @Column(name = "task_status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority", nullable = false)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_status", nullable = false)
    private DataStatus dataStatus = DataStatus.ACTIVE;

    @Column(name = "archived_at")
    private Instant archivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @Column
    private Instant dueDate;

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
        this.dataStatus = DataStatus.ACTIVE;
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

    public void changeStatus(TaskStatus status) {

        this.status = (status != null)
                ? status
                : TaskStatus.TODO;
    }

    public void isArchived() {

        if (this.dataStatus == DataStatus.ARCHIVED) {
            return;
        }
        this.dataStatus = DataStatus.ARCHIVED;
        this.archivedAt = Instant.now();
    }
}
