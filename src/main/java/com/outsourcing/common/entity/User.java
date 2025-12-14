package com.outsourcing.common.entity;

import com.outsourcing.common.enums.IsDeleted;
import com.outsourcing.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;//사용자 아이디

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;//실명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IsDeleted isDeleted =IsDeleted.FALSE;

    @OneToMany(mappedBy = "user")
    private final List<TeamMember> members = new ArrayList<>();

    public User(String username, String email, String password, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void modify(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void softDelete(IsDeleted isDeleted){
        this.isDeleted = isDeleted;
    }
}
