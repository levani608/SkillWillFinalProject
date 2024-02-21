package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.StockEntity;
import com.study.projects.newphotoproject.model.domain.database.StockHistoryEntity;
import com.study.projects.newphotoproject.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryEntity findByStock(StockEntity stock) {
        return stockHistoryRepository.findByStockEntity(stock).orElse(null);
    }

    public StockHistoryEntity saveStockHistory(StockHistoryEntity stockHistory) {
        return stockHistoryRepository.saveAndFlush(stockHistory);
    }

    public List<StockHistoryEntity> findAllStockHistory() {
        return stockHistoryRepository.findAll();
    }

    public List<StockHistoryEntity> findByStockId(Long stockId) {
        return stockHistoryRepository.findAllByStockEntityStockId(stockId);
    }

    public List<StockHistoryEntity> findHistoryByDateRange(Long stockId, LocalDate from, LocalDate to) {
        return stockHistoryRepository.findAllByDateRange(stockId, from, to);
    }
}
