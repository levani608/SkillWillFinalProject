package com.study.projects.newphotoproject.model.domain.database;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity(name = "stock")
@Getter
@Setter
@RequiredArgsConstructor
@Check(constraints = "quantity >= 0")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @CreationTimestamp
    protected LocalDate createdAt;

    @OneToOne
    @JoinColumn(name = "server_plan_id")
    private ServerPlanEntity serverPlanEntity;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "stockEntity")
    private Set<StockHistoryEntity> stockHistory;

    /*@Version
    private Long version;*/

}
