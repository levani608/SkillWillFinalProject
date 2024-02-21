package com.study.projects.newphotoproject.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockDto {

    private Long stockId;

    private Long serverPlanId;

    private Integer quantity;

}
