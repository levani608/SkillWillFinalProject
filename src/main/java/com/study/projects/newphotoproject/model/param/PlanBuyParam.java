package com.study.projects.newphotoproject.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class PlanBuyParam {

    private Long serverPlanId;

    private Long userWalletId;

    private String userServerName;

}
