package com.study.projects.newphotoproject.model.param;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyParam {

    @NotNull(message = "amount can not be null!;")
    private Double amount;
}
