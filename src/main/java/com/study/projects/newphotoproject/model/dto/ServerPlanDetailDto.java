package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@Setter
//@RedisHash
public class ServerPlanDetailDto extends ServerPlanDto {

    private Long serverPlanId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
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
