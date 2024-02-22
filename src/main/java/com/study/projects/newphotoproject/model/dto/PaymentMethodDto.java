package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PaymentMethodDto {

    private Long paymentId;

    private String paymentMethodName;

    private Double rate;

    private PaymentMethodStatus paymentMethodStatus;

}
