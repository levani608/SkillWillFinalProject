package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.dto.ServerPlanDetailDto;
import com.study.projects.newphotoproject.model.dto.ServerPlanDto;

public class ServerPlanMapper {

    public static ServerPlanDto toServerPlanDto(ServerPlanEntity serverPlanEntity) {
        return new ServerPlanDto(serverPlanEntity.getServerPlanName(), serverPlanEntity.getServerPlanCapacity(), serverPlanEntity.getPrice());
    }

    public static ServerPlanDetailDto toServerPlanDetailDto(ServerPlanEntity serverPlanEntity) {
        return new ServerPlanDetailDto(serverPlanEntity.getServerPlanId(), serverPlanEntity.getServerPlanName(), serverPlanEntity.getServerPlanCapacity(),
                serverPlanEntity.getQuantity(), serverPlanEntity.getPrice(), serverPlanEntity.getServerPlanStatus());
    }

}
