package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RedisHash
public class ServerPlanDto implements Serializable {

    protected String serverPlanName;

    protected Double maxCapacity;

    protected Double price;

}
