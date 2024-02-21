package com.study.projects.newphotoproject.model.param;

import lombok.Data;

@Data
public class AddServerPlanParam {

    private String serverPlanName;

    private Double maxCapacity;

    private Double price;

}
