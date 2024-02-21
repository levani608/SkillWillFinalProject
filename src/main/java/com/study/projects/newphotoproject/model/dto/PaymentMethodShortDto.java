package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentMethodShortDto {

    private String paymentMethodName;

    private Double rate;

}
