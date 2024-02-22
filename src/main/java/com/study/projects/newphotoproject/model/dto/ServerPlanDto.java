package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerPlanDto implements Serializable {

    protected String serverPlanName;

    protected Double maxCapacity;

    protected Double price;

}
