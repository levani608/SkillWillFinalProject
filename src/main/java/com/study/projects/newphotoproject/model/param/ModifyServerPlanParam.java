package com.study.projects.newphotoproject.model.param;

import lombok.Data;

@Data
public class ModifyServerPlanParam {

    private String serverPlanName;
    private Integer quantity;

    private Double price;

}
