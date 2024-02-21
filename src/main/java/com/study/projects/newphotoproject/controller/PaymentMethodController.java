package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.PaymentMethodFacade;
import com.study.projects.newphotoproject.model.dto.PaymentMethodShortDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/photo-project/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodFacade paymentMethodFacade;

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<PaymentMethodShortDto>> getPaymentMethods() {
        return new ResponseEntity<>(paymentMethodFacade.getPaymentMethodsShort(), HttpStatus.OK);
    }

}
