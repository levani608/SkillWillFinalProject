package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "server_plans")
@Getter
@Setter
@RequiredArgsConstructor
public class ServerPlanEntity extends TableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_plan_id")
    private Long serverPlanId;

    @Column(name = "server_plan_name", nullable = false, unique = true)
    private String serverPlanName;

    @Column(name = "server_plan_capacity", nullable = false, unique = true)
    private Double serverPlanCapacity;

    @Column(name = "server_plan_price", nullable = false)
    private Double price;

    @Column(name = "server_plan_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServerPlanStatus serverPlanStatus;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "serverPlanEntity")
    private Set<UserServerEntity> userServers;

}
