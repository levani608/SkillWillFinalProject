package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.UserServerByPlanNameDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.service.ServerPlanService;
import com.study.projects.newphotoproject.service.UserServerService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServerFacade {

    private final UserServerService userServerService;

    private final UserService userService;

    private final ServerPlanService serverPlanService;

    public List<UserServerDto> getUserServers(Long userId) {

        return null;
    }


    public UserServerByPlanNameDto getUserServersByPlanName(Long userId, String serverPlanName) {

        return null;
    }

    public UserServerDto getFreeServer(Long userId) {

        return null;
    }

}
