package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import com.study.projects.newphotoproject.repository.ServerPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerPlanService {

    private final ServerPlanRepository serverPlanRepository;

    public List<ServerPlanEntity> getAllServerPlans() {
        log.debug("getAllServerPlans");
        return serverPlanRepository.findAll().stream().toList();
    }

    public Optional<ServerPlanEntity> findServerPlanById(Long id) {
        return serverPlanRepository.findByServerPlanId(id);
    }

    public Optional<ServerPlanEntity> findByServerPlanName(String name) {
        return serverPlanRepository.findByServerPlanName(name);
    }

    public ServerPlanEntity saveServerPlan(ServerPlanEntity serverPlan) {
        return serverPlanRepository.saveAndFlush(serverPlan);
    }


    public Boolean checkServerPlanExistenceByName(String serverPlanName) {
        return serverPlanRepository.existsByServerPlanName(serverPlanName);
    }

    public Optional<ServerPlanEntity> findCheapestServerPlan() {
        return serverPlanRepository.findCheapestValidPlan();
    }
}
