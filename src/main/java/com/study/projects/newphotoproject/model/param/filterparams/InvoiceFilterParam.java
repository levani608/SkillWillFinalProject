package com.study.projects.newphotoproject.model.param.filterparams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InvoiceFilterParam {

    List<Long> invoiceIds;

    List<Long> userServerIds;

    List<Long> transactionIds;

    LocalDate from;

    LocalDate to;

}
