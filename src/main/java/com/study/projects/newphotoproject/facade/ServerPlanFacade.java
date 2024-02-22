package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import com.study.projects.newphotoproject.model.dto.ServerPlanDetailDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.mapper.ServerPlanMapper;
import com.study.projects.newphotoproject.model.mapper.UserServerMapper;
import com.study.projects.newphotoproject.model.param.AddServerPlanParam;
import com.study.projects.newphotoproject.model.param.ModifyServerPlanParam;
import com.study.projects.newphotoproject.model.param.PlanBuyParam;
import com.study.projects.newphotoproject.repository.ServerPlanRepository;
import com.study.projects.newphotoproject.service.ServerPlanService;
import com.study.projects.newphotoproject.service.UserServerService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

        List<ServerPlanDetailDto> serverPlans = serverPlanService.getAllServerPlans().stream()
                .map(ServerPlanMapper::toServerPlanDetailDto).toList();

        return serverPlans;

    }

    public ServerPlanDetailDto getServerPlanById(Long id) {

        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found"));

        if (serverPlan == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found!");

        return ServerPlanMapper.toServerPlanDetailDto(serverPlan);
    }



    public ServerPlanDetailDto addServerPlan(AddServerPlanParam addServerPlanParam) {

        ServerPlanEntity serverPlan = new ServerPlanEntity();


        serverPlan.setServerPlanName(addServerPlanParam.getServerPlanName());
        serverPlan.setServerPlanCapacity(addServerPlanParam.getMaxCapacity());
        serverPlan.setPrice(addServerPlanParam.getPrice());
        serverPlan.setQuantity(addServerPlanParam.getQuantity());
        serverPlan.setServerPlanStatus(ServerPlanStatus.VALID);

        serverPlanService.saveServerPlan(serverPlan);

        return ServerPlanMapper.toServerPlanDetailDto(serverPlan);
    }


    public ServerPlanDetailDto modifyServerPlan(Long serverPlanId, ModifyServerPlanParam modifyServerPlanParam) {

        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(serverPlanId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found"));;


        serverPlan.setServerPlanName(modifyServerPlanParam.getServerPlanName());
        serverPlan.setPrice(modifyServerPlanParam.getPrice());
        serverPlan.setQuantity(modifyServerPlanParam.getQuantity());
        serverPlanService.saveServerPlan(serverPlan);

        ServerPlanDetailDto serverPlanDetailDto = ServerPlanMapper.toServerPlanDetailDto(serverPlan);

        return serverPlanDetailDto;
    }


    public ServerPlanDetailDto invalidateServerPlan(Long serverPlanId) {

        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(serverPlanId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found"));;

        serverPlan.setServerPlanStatus(ServerPlanStatus.INVALID);
        serverPlanService.saveServerPlan(serverPlan);

        ServerPlanDetailDto serverPlanDetailDto = ServerPlanMapper.toServerPlanDetailDto(serverPlan);

        return serverPlanDetailDto;
    }


    public ServerPlanDetailDto activateServerPlan(Long serverPlanId) {

        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(serverPlanId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found"));;

        serverPlan.setServerPlanStatus(ServerPlanStatus.VALID);
        serverPlanService.saveServerPlan(serverPlan);

        ServerPlanDetailDto serverPlanDetailDto = ServerPlanMapper.toServerPlanDetailDto(serverPlan);

        return serverPlanDetailDto;
    }
    @Transactional
    public UserServerDto buyPlan(Long userId, PlanBuyParam planBuyParam) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus() == UserStatus.DEACTIVATED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User deactivated!");
        }


        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(planBuyParam.getServerPlanId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found!"));
        if (serverPlan.getServerPlanStatus()==ServerPlanStatus.INVALID) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Server plan invalid!");
        }


        double serverPlanPrice = serverPlan.getPrice();


        if (serverPlan.getQuantity() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough items in stock!");
        }
        else if (user.getBalance() < serverPlanPrice) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds on the wallet!");
        }

        user.setBalance(user.getBalance()-serverPlanPrice);
        serverPlan.setQuantity(serverPlan.getQuantity()-1);


        UserServerEntity userServerEntity = userServerService.saveUserServer(new UserServerEntity(planBuyParam.getUserServerName(), user, serverPlan));

        serverPlanService.saveServerPlan(serverPlan);
        userService.saveUser(user);
        return UserServerMapper.toUserServerDto(userServerEntity);
    }


}
