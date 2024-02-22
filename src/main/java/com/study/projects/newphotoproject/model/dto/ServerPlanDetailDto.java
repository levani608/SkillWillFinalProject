package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class ServerPlanDetailDto extends ServerPlanDto {

    private Long serverPlanId;

    private Integer quantity;

    private ServerPlanStatus serverPlanStatus;

    public ServerPlanDetailDto(Long serverPlanId, String serverPlanName, Double maxCapacity, Integer quantity, Double price, ServerPlanStatus serverPlanStatus) {
        this.serverPlanId = serverPlanId;
        this.serverPlanName = serverPlanName;
        this.maxCapacity = maxCapacity;
        this.quantity = quantity;
        this.price = price;
        this.serverPlanStatus = serverPlanStatus;
    }

}
