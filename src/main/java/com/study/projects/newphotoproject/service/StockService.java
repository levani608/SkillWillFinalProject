package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.domain.database.StockEntity;
import com.study.projects.newphotoproject.model.dto.StockDto;
import com.study.projects.newphotoproject.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public StockEntity saveStock(StockEntity stock) {
        return stockRepository.saveAndFlush(stock);
    }

    public Optional<StockEntity> findByServerPlanEntity(ServerPlanEntity serverPlan) {
        return stockRepository.findByServerPlanEntity(serverPlan);
    }

    public List<StockEntity> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<StockEntity> findStockById(Long stockId) {
        return stockRepository.findById(stockId);
    }
}
