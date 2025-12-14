package com.outsourcing.common.entity;

import com.outsourcing.common.entity.task.Task;
import com.outsourcing.domain.activities.dto.ActivityType;
import com.outsourcing.domain.activities.dto.response.ActivitiesAllResponse;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public Activity(ActivityType type, Instant timestamp, String description, User user, Task task) {
        this.type = type;
        this.timestamp = timestamp;
        this.description = description;
        this.user = user;
        this.task = task;
    }

    public static Activity of(ActivityType type, Instant timestamp, String description, User user, Task task) {
        return new Activity(type, timestamp, description, user, task);
    }

    // 전체 활동 로그 조회 쪽 반환형
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

    // 내 활동 로그 조회 쪽 반환형
    public static ActivitiesAllResponse fromAll(Activity activity) {
        return ActivitiesAllResponse.of(
                activity.id,
                activity.user.getId(),
                activity.user,
                activity.type,
                activity.task.getId(),
                activity.createdAt,
                activity.description
        );
    }
}