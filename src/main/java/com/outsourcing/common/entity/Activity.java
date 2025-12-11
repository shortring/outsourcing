package com.outsourcing.common.entity;

import com.outsourcing.common.entity.task.Task;
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
    private String type;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String description;

    // user, task 연관관계 검토 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id")
//    private Task task;

    public Activity(String type, Long userId, Long taskId, Instant timestamp, String description) {
        this.type = type;
        this.userId = userId;
        this.taskId = taskId;
        this.timestamp = timestamp;
        this.description = description;
    }

    public static Activity of(String type, Long userId, Long taskId, Instant timestamp, String description) {
        return new Activity(type, userId, taskId, timestamp, description);
    }

    public static ActivitiesResponse from(Activity activity) {
        return ActivitiesResponse.of(
                activity.id,
                activity.type,
                activity.userId,
                activity.user,
                activity.taskId,
                activity.timestamp,
                activity.description
        );
    }
}