package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerPlanRepository extends JpaRepository<ServerPlanEntity, Long> {

    Optional<ServerPlanEntity> findByServerPlanId(Long id);

    Optional<ServerPlanEntity> findByServerPlanName(String name);

    Boolean existsByServerPlanName(String name);

    //@Query("select s from server_plans s where s.price = (select min(p.price) from server_plans p) and s.serverPlanStatus = 'VALID'")
    @Query("select s from server_plans s where s.serverPlanStatus = 'VALID' order by s.price asc")
    Optional<ServerPlanEntity> findCheapestValidPlan();

}
