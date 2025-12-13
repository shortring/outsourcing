package com.outsourcing.common.event;


import com.outsourcing.common.dto.ActivityCreatedEvent;
import com.outsourcing.common.entity.task.Task;

import com.outsourcing.domain.activities.repository.ActivityRepository;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.Activity;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ActivityEventListener {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void on(ActivityCreatedEvent event){

        User user = (event.userId()==null)
                ? null
                :userRepository.getReferenceById(event.userId());
        Task task = (event.taskId()==null)
                ? null
                : taskRepository.getReferenceById(event.taskId());

        activityRepository.save(
                Activity.of(
                        event.type(),
                        event.timestamp(),
                        event.description(),
                        user,
                        task
                )
        );
    }
}
