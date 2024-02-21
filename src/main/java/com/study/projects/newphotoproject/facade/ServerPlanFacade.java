package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.domain.database.StockEntity;
import com.study.projects.newphotoproject.model.domain.database.StockHistoryEntity;
import com.study.projects.newphotoproject.model.dto.ServerPlanDetailDto;
import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import com.study.projects.newphotoproject.model.mapper.ServerPlanMapper;
import com.study.projects.newphotoproject.model.param.AddServerPlanParam;
import com.study.projects.newphotoproject.model.param.ModifyServerPlanParam;
import com.study.projects.newphotoproject.repository.ServerPlanRepository;
import com.study.projects.newphotoproject.service.ServerPlanService;
import com.study.projects.newphotoproject.service.StockHistoryService;
import com.study.projects.newphotoproject.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerPlanFacade {

    private final ServerPlanService serverPlanService;
    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    private final BeanFactory beanFactory;
    private final ServerPlanRepository serverPlanRepository;

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
        StockEntity stock = new StockEntity();
        StockHistoryEntity stockHistory = new StockHistoryEntity();

        serverPlan.setServerPlanName(addServerPlanParam.getServerPlanName());
        serverPlan.setServerPlanCapacity(addServerPlanParam.getMaxCapacity());
        serverPlan.setPrice(addServerPlanParam.getPrice());
        serverPlan.setServerPlanStatus(ServerPlanStatus.VALID);
        serverPlanService.saveServerPlan(serverPlan);

        stock.setServerPlanEntity(serverPlan);
        stock.setQuantity(0);
        stockService.saveStock(stock);

        stockHistory.setStockEntity(stock);
        stockHistory.setDelta(0);
        stockHistoryService.saveStockHistory(stockHistory);
        serverPlan.setStock(stock);

        ServerPlanDetailDto serverPlanDetailDto = ServerPlanMapper.toServerPlanDetailDto(serverPlan);

        return serverPlanDetailDto;
    }


    public ServerPlanDetailDto modifyServerPlan(Long serverPlanId, ModifyServerPlanParam modifyServerPlanParam) {

        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(serverPlanId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found"));;


        serverPlan.setServerPlanName(modifyServerPlanParam.getServerPlanName());
        serverPlan.setPrice(modifyServerPlanParam.getPrice());
        serverPlanService.saveServerPlan(serverPlan);

        ServerPlanDetailDto serverPlanDetailDto = ServerPlanMapper.toServerPlanDetailDto(serverPlan);

        return serverPlanDetailDto;
    }

    @Caching(evict = {
            @CacheEvict(value= "serverplandetaildto", key = "#root.target.ALL"),
            @CacheEvict(value= "serverplandetaildto", key = "#result.serverPlanId"),
            @CacheEvict(value = "serverplandtos", allEntries = true)

    })
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



}
