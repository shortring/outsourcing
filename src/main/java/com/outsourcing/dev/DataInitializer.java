package com.outsourcing.dev;

import com.outsourcing.common.entity.Team;
import com.outsourcing.common.entity.TeamMember;
import com.outsourcing.common.entity.User;
import com.outsourcing.common.entity.task.Task;
import com.outsourcing.common.entity.task.TaskPriority;
import com.outsourcing.common.entity.task.TaskStatus;
import com.outsourcing.common.enums.UserRole;
import com.outsourcing.domain.task.repository.TaskRepository;
import com.outsourcing.domain.team.repository.TeamRepository;
import com.outsourcing.domain.teamMember.repository.TeamMemberRepository;
import com.outsourcing.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("test1", "test1@test.com", passwordEncoder.encode("123456789Aa!"), "choi_test1", UserRole.ADMIN);
        User user2 = new User("test2", "test2@test.com", passwordEncoder.encode("123456789Aa!"), "choi_test2", UserRole.USER);
        User user3 = new User("test3", "test3@test.com", passwordEncoder.encode("123456789Aa!"), "choi_test3", UserRole.USER);
        User user4 = new User("test4", "test4@test.com", passwordEncoder.encode("123456789Aa!"), "choi_test4", UserRole.USER);
        User user5 = new User("test5", "test5@test.com", passwordEncoder.encode("123456789Aa!"), "choi_test5", UserRole.USER);
        User user6 = new User("test6", "test6@test.com", passwordEncoder.encode("123456789Aa!"), "choi_test6", UserRole.USER);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);

        Team team1 = new Team("테스트 팀명1", "설명 테스트1");
        Team team2 = new Team("테스트 팀명2", "설명 테스트2");

        teamRepository.save(team1);
        teamRepository.save(team2);

        TeamMember teamMember1 = new TeamMember(team1, user1);
        TeamMember teamMember2 = new TeamMember(team1, user2);
        TeamMember teamMember3 = new TeamMember(team1, user3);
        TeamMember teamMember4 = new TeamMember(team2, user4);
        TeamMember teamMember5 = new TeamMember(team2, user5);
        TeamMember teamMember6 = new TeamMember(team2, user6);

        teamMemberRepository.save(teamMember1);
        teamMemberRepository.save(teamMember2);
        teamMemberRepository.save(teamMember3);
        teamMemberRepository.save(teamMember4);
        teamMemberRepository.save(teamMember5);
        teamMemberRepository.save(teamMember6);

        Task task1 = new Task("테스트 작업명1", "설명 테스트1", TaskPriority.HIGH, user1, LocalDate.of(2025, 12, 13).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task2 = new Task("테스트 작업명2", "설명 테스트2", TaskPriority.HIGH, user1, LocalDate.of(2025, 12, 11).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task3 = new Task("테스트 작업명3", "설명 테스트3", TaskPriority.HIGH, user2, LocalDate.of(2025, 12, 19).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task4 = new Task("테스트 작업명4", "설명 테스트4", TaskPriority.HIGH, user3, LocalDate.of(2025, 12, 10).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task5 = new Task("테스트 작업명5", "설명 테스트5", TaskPriority.HIGH, user4, LocalDate.of(2025, 12, 11).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task6 = new Task("테스트 작업명6", "설명 테스트6", TaskPriority.MEDIUM, user5, LocalDate.of(2025, 12, 11).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task7 = new Task("테스트 작업명7", "설명 테스트7", TaskPriority.LOW, user6, LocalDate.of(2025, 12, 9).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task8 = new Task("테스트 작업명8", "설명 테스트8", TaskPriority.HIGH, user2, LocalDate.of(2025, 12, 11).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task9 = new Task("테스트 작업명9", "설명 테스트9", TaskPriority.MEDIUM, user3, LocalDate.of(2025, 12, 12).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task10 = new Task("테스트 작업명10", "설명 테스트10", TaskPriority.LOW, user4, LocalDate.of(2025, 12, 8).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task task11 = new Task("테스트 작업명11", "설명 테스트11", TaskPriority.HIGH, user1, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Task task12 = new Task("테스트 작업명12", "설명 테스트12", TaskPriority.MEDIUM, user1, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Task futureTask = new Task("user2 미래 작업", "아직 기한 남음", TaskPriority.HIGH, user2, LocalDate.of(2025, 12, 15).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task todayTask = new Task("user2 오늘 작업", "오늘까지 해야 함", TaskPriority.MEDIUM, user2, LocalDate.of(2025, 12, 11).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        Task overdueTask = new Task("user2 지연 작업", "이미 마감일 지남", TaskPriority.LOW, user2, LocalDate.of(2025, 12, 9).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());


        task1.changeStatus(TaskStatus.IN_PROGRESS);
        task2.changeStatus(TaskStatus.DONE);
        task3.changeStatus(TaskStatus.IN_PROGRESS);
        task4.changeStatus(TaskStatus.IN_PROGRESS);
        task5.changeStatus(TaskStatus.IN_PROGRESS);
        task6.changeStatus(TaskStatus.IN_PROGRESS);
        task7.changeStatus(TaskStatus.TODO);
        task8.changeStatus(TaskStatus.DONE);
        task9.changeStatus(TaskStatus.IN_PROGRESS);
        task10.changeStatus(TaskStatus.TODO);
        task11.changeStatus(TaskStatus.TODO);
        task12.changeStatus(TaskStatus.DONE);
        futureTask.changeStatus(TaskStatus.IN_PROGRESS);
        todayTask.changeStatus(TaskStatus.TODO);
        overdueTask.changeStatus(TaskStatus.IN_PROGRESS);

//        Task task1 = "2025-12-13T23:59:59Z"));
//        Task task2 = "2025-12-11T23:59:59Z"));
//        Task task3 = "2025-12-19T23:59:59Z"));
//        Task task4 = "2025-12-10T23:59:59Z"));
//        Task task5 = "2025-12-11T23:59:59Z"));
//        Task task6 = "2025-12-11T23:59:59Z"));
//        Task task7 = "2025-12-09T23:59:59Z"));
//        Task task8 = "2025-12-11T23:59:59Z"));
//        Task task9 = "2025-12-12T23:59:59Z"));
//        Task task10= "2025-12-08T23:59:59Z"));
//        Task task11= Instant.now());
//        Task task12= Instant.now());
//
//        Task futureTask = "2025-12-15T23:59:59Z"));
//        Task todayTask = "2025-12-11T23:59:59Z"));
//        Task overdueTask = "2025-12-09T23:59:59Z"));

        taskRepository.save(futureTask);
        taskRepository.save(todayTask);
        taskRepository.save(overdueTask);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
        taskRepository.save(task5);
        taskRepository.save(task6);
        taskRepository.save(task7);
        taskRepository.save(task8);
        taskRepository.save(task9);
        taskRepository.save(task10);
        taskRepository.save(task11);
        taskRepository.save(task12);
    }
}
