package com.study.projects.newphotoproject.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddModifyPaymentMethodParam {

    private String paymentMethodName;

    private Double paymentRate;

}
