package com.outsourcing.common.entity;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "activities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ActivityType type;

//    @Column(nullable = false, unique = true)
//    private Long userId;
//
//    @Column(nullable = false)
//    private Long taskId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String description;

    // user, task 연관관계 검토 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public Activity(ActivityType type, Instant timestamp, String description, User user, Task task) {
        this.type = type;
//        this.userId = userId;
//        this.taskId = taskId;
        this.timestamp = timestamp;
        this.description = description;
        this.user = user;
        this.task = task;
    }

    public static Activity of(ActivityType type, Instant timestamp, String description, User user, Task task) {
        return new Activity(type, timestamp, description, user, task);
    }

    public static ActivitiesResponse from(Activity activity) {
        return ActivitiesResponse.of(
                activity.id,
                activity.type,
                activity.user.getId(),
                activity.user,
                activity.task.getId(),
                activity.timestamp,
                activity.description
        );
    }
}