package com.outsourcing.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team_members")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class TeamMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "team_id")
    private Team team;


    public TeamMember(Team team, User user) {
        this.team = team;
        this.user = user;
    }


}
