package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.StockHistoryEntity;
import com.study.projects.newphotoproject.model.dto.StockHistoryDto;

public class StockHistoryMapper {

    public static StockHistoryDto stockHistoryDto(StockHistoryEntity stockHistoryEntity) {
        return new StockHistoryDto(stockHistoryEntity.getStockHistoryId(), stockHistoryEntity.getStockEntity().getStockId(),
                stockHistoryEntity.getCreatedAt(), stockHistoryEntity.getDelta());
    }

}
