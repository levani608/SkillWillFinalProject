package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.ServerPlanDetailDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.model.param.AddServerPlanParam;
import com.study.projects.newphotoproject.model.param.ModifyServerPlanParam;
import com.study.projects.newphotoproject.model.param.PlanBuyParam;
import com.study.projects.newphotoproject.repository.ServerPlanRepository;
import com.study.projects.newphotoproject.service.ServerPlanService;
import com.study.projects.newphotoproject.service.UserServerService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerPlanFacade {

    private final ServerPlanService serverPlanService;
    private final BeanFactory beanFactory;
    private final ServerPlanRepository serverPlanRepository;
    private final UserService userService;
    private final UserServerService userServerService;
    public static final String ALL = "all";

    public List<ServerPlanDetailDto> getAllServerPlans() {

        return null;

    }

    public ServerPlanDetailDto getServerPlanById(Long id) {

        return null;
    }


    public ServerPlanDetailDto addServerPlan(AddServerPlanParam addServerPlanParam) {

        return null;
    }


    public ServerPlanDetailDto modifyServerPlan(Long serverPlanId, ModifyServerPlanParam modifyServerPlanParam) {

        return null;
    }


    public ServerPlanDetailDto invalidateServerPlan(Long serverPlanId) {

        return null;
    }


    public ServerPlanDetailDto activateServerPlan(Long serverPlanId) {

        return null;
    }

    @Transactional
    public UserServerDto buyPlan(Long userId, PlanBuyParam planBuyParam) {

        return null;
    }


}
