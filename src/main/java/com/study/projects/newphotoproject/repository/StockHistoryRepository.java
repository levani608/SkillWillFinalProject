package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.StockEntity;
import com.study.projects.newphotoproject.model.domain.database.StockHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistoryEntity, Long> {

    Optional<StockHistoryEntity> findByStockEntity(StockEntity stock);

    List<StockHistoryEntity> findAllByStockEntityStockId(Long stockId);

    @Query("select sh from stock_history sh where sh.stockEntity.stockId = :stockId and sh.createdAt >= :from and sh.createdAt <= :to")
    List<StockHistoryEntity> findAllByDateRange(Long stockId, LocalDate from, LocalDate to);

}
