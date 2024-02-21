package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.ServerFacade;
import com.study.projects.newphotoproject.model.dto.ServerPlanDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.model.param.PlanBuyParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photo-project/servers-plans") //authorized
@RequiredArgsConstructor
public class ServerController {

    private final ServerFacade serverFacade;

    @GetMapping
    public ResponseEntity<List<ServerPlanDto>> getServerPlans() {
        return new ResponseEntity<>(serverFacade.getServerPlans(),HttpStatus.OK);
    }


    @PostMapping("/{userId}/buy-plan")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<UserServerDto>> buyServerPlan(@PathVariable Long userId, @RequestBody PlanBuyParam planBuyParam) {
        return new ResponseEntity<>(serverFacade.buyPlan(userId, planBuyParam),HttpStatus.OK);
    }

}
