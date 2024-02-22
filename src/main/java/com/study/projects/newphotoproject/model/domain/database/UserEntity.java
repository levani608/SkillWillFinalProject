package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.UserRole;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserEntity extends TableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "serverUserEntity")
    private Set<UserServerEntity> userServers;

    @OneToMany(mappedBy = "fromUserEntity")
    private Set<ShareRightsEntity> fromUsers;

    @OneToMany(mappedBy = "toUserEntity")
    private Set<ShareRightsEntity> toUsers;

    private Double balance;


}
