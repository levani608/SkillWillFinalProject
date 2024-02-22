package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserServerDto {

    private Long userServerId;

    private String userServerName;

    private String ServerPlanName;

    private Double usedCapacity;

    private Double maxCapacity;

}
