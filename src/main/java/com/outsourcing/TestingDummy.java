package com.outsourcing;

import com.outsourcing.common.entity.Activity;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.enums.UserRole;
import com.outsourcing.domain.activities.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

import static com.outsourcing.domain.activities.dto.ActivityType.TASK_CREATED;

@Configuration
@RequiredArgsConstructor
public class TestingDummy {
    private final ActivityRepository activityRepository;

    User user1 = new User("user1", "email1@naver.com", "1234@!ASDddd", "name1", UserRole.USER);
    User user2 = new User("user2", "email2@naver.com", "1234@!ASDddd", "name2", UserRole.USER);
    User user3 = new User("user3", "email3@naver.com", "1234@!ASDddd", "name3", UserRole.ADMIN);
    Task task1 = new Task("title", "description", TaskPriority.HIGH, user1, Instant.now());
    Task task2 = new Task("title1", "description1", TaskPriority.LOW, user2, Instant.now());
    Task task3 = new Task("title2", "description2", TaskPriority.MEDIUM, user3, Instant.now());
    Activity activity1 = Activity.of(TASK_CREATED, Instant.now(), "description1", user1, task1);
    Activity activity2 = Activity.of(TASK_CREATED, Instant.now(), "description1", user2, task2);
    Activity activity3 = Activity.of(TASK_CREATED, Instant.now(), "description1", user3, task3);

    public void testSave() {
        activityRepository.save(activity1);
        activityRepository.save(activity2);
        activityRepository.save(activity3);
    }
}
