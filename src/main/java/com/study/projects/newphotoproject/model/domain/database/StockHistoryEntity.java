package com.study.projects.newphotoproject.model.domain.database;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "stock_history")
public class StockHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_history_id")
    private Long stockHistoryId;

    @CreationTimestamp
    protected LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stockEntity;

    @Column(name = "delta", nullable = false)
    private Integer delta;

    public StockHistoryEntity(StockEntity stock, Integer delta) {
        this.stockEntity = stock;
        this.delta = delta;
    }


}
