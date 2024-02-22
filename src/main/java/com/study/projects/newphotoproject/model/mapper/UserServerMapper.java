package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import com.study.projects.newphotoproject.model.dto.UserServerDto;

public class UserServerMapper {

    public static UserServerDto toUserServerDto(UserServerEntity userServerEntity) {
        return new UserServerDto(userServerEntity.getUserServerId(),userServerEntity.getUserServerName(), userServerEntity.getServerPlanEntity().getServerPlanName(),
                userServerEntity.getUsedCapacity(), userServerEntity.getServerPlanEntity().getServerPlanCapacity());
    }

}
