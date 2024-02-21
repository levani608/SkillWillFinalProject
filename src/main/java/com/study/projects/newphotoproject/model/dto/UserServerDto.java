package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserServerDto {

    private String userServerName;

    private String ServerPlanName;

    private Double usedCapacity;

    private Double maxCapacity;

}
