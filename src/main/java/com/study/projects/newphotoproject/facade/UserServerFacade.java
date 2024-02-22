package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import com.study.projects.newphotoproject.model.dto.UserServerByPlanNameDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.model.enums.UserServerStatus;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.mapper.UserServerMapper;
import com.study.projects.newphotoproject.service.ServerPlanService;
import com.study.projects.newphotoproject.service.UserServerService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServerFacade {

    private final UserServerService userServerService;

    private final UserService userService;

    private final ServerPlanService serverPlanService;

    public List<UserServerDto> getUserServers(Long userId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");

        return userServerService.getUserServersByUserId(userId).stream()
                .map(UserServerMapper::toUserServerDto).toList();
    }


    public UserServerByPlanNameDto getUserServersByPlanName(Long userId, String serverPlanName) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");
        if (!serverPlanService.checkServerPlanExistenceByName(serverPlanName))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found!");

        int quantity = (int)userServerService.getUserServersByUserId(userId).stream()
                .filter(us -> Objects.equals(us.getServerPlanEntity().getServerPlanName(), serverPlanName)).count();

        return new UserServerByPlanNameDto(serverPlanName, quantity);
    }

    public UserServerDto getFreeServer(Long userId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");

        Set<UserServerEntity> userServers = user.getUserServers();
        if (!userServers.isEmpty())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already has a server!");
        else {
            UserServerEntity userServer = new UserServerEntity();
            ServerPlanEntity serverPlan = serverPlanService.findCheapestServerPlan().orElseThrow(()->
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There were no valid server plans to give to users!"));

            userServer.setServerUserEntity(user);
            userServer.setServerPlanEntity(serverPlan);
            userServer.setUsedCapacity(0.0);
            userServer.setUserServerName(serverPlan.getServerPlanName());
            userServer.setUserServerStatus(UserServerStatus.ACTIVE);

            userServerService.saveUserServer(userServer);

            return UserServerMapper.toUserServerDto(userServer);
        }

    }

}
