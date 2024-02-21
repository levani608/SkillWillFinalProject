package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDto {

    private Long transactionId;

    private LocalDate transactionDate;

    private Double amount;

}
