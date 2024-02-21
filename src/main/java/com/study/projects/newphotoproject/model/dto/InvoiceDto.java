package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class InvoiceDto {

    private Long invoiceId;

    private LocalDate invoiceDate;

    private Long transactionId;

    private Long userServerId;

    private WalletTransactionDto transaction;

}
