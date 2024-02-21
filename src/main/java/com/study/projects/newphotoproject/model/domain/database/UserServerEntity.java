package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.UserServerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "user_servers")
@Getter
@Setter
@RequiredArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_server_name", "server_owner_id"})})
public class UserServerEntity extends TableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_server_id")
    private Long userServerId;

    @Column(name = "user_server_name")
    private String userServerName;

    @ManyToOne
    @JoinColumn(name = "server_owner_id")
    private UserEntity serverUserEntity;

    @ManyToOne
    @JoinColumn(name = "server_plan_id")
    private ServerPlanEntity serverPlanEntity;

    @Column(name = "used_capacity",nullable = false)
    private Double usedCapacity;

    @Column(name = "user_server_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserServerStatus userServerStatus;

    @OneToOne(mappedBy = "userServerEntity")
    private InvoiceEntity invoice;

    @OneToMany(mappedBy = "serverEntity")
    private Set<AlbumEntity> albums;

    public UserServerEntity(String name, UserEntity owner, ServerPlanEntity serverPlan, Double usedCapacity) {
        this.userServerName = name;
        this.serverUserEntity = owner;
        this.serverPlanEntity = serverPlan;
        this.usedCapacity = usedCapacity;
        this.userServerStatus = UserServerStatus.ACTIVE;
    }

}
