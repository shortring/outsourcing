package com.outsourcing.domain.team.service;

import com.outsourcing.common.exception.CustomException;
import com.outsourcing.common.exception.ErrorMessage;
import com.outsourcing.domain.teamMember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TeamValidateService {

    private final TeamMemberRepository teamMemberRepository;

    // 팀 중복 검사
    @Transactional(readOnly = true)
    public void ValidateUser(Long teamId, Long userId, ErrorMessage errorMessage) {

        boolean exists = teamMemberRepository.existsByTeam_IdAndUser_Id(teamId, userId);

        if (!exists) {
            throw new CustomException(errorMessage);
        }
    }
}
