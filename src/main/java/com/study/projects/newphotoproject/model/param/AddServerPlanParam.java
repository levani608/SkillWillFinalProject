package com.study.projects.newphotoproject.model.param;

import lombok.Data;

@Data
public class AddServerPlanParam {

    private String serverPlanName;

    private Double maxCapacity;
    private Integer quantity;
    private Double price;

}
