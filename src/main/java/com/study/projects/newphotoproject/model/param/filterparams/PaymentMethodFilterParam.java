package com.study.projects.newphotoproject.model.param.filterparams;

import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaymentMethodFilterParam {

    private List<Long> paymentMethodIds;

    private String paymentMethodName;

    private List<Double> paymentRates;

    private List<PaymentMethodStatus> paymentMethodStatuses;

    private LocalDate createdFrom;

    private LocalDate createdTo;

    private LocalDate updatedFrom;

    private LocalDate updatedTo;
}


