package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class PaymentMethodDto {

    private Long paymentId;

    private String paymentMethodName;

    private Double rate;

    @Enumerated(EnumType.STRING)
    private PaymentMethodStatus paymentMethodStatus;

}
