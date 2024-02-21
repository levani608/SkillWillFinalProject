package com.study.projects.newphotoproject.model.param.filterparams;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InvoiceFilterFieldParam {

    private List<String> fieldNames;

    List<Long> invoiceIds;

    List<Long> userServerIds;

    List<Long> transactionIds;

    LocalDate from;

    LocalDate to;

}
