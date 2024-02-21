package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.StockEntity;
import com.study.projects.newphotoproject.model.dto.StockDto;

public class StockMapper {

    public static StockDto toStockDto(StockEntity stockEntity) {
        return new StockDto(stockEntity.getStockId(), stockEntity.getServerPlanEntity().getServerPlanId(), stockEntity.getQuantity());
    }

}
