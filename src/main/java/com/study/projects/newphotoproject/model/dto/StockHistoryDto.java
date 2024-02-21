package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class StockHistoryDto {

    private Long stockHistoryId;

    private Long stockId;

    private LocalDate date;

    private Integer delta;

}
