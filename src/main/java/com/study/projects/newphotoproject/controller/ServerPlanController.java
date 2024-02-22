package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.ServerPlanFacade;
import com.study.projects.newphotoproject.model.dto.ServerPlanDetailDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.model.param.AddServerPlanParam;
import com.study.projects.newphotoproject.model.param.ModifyServerPlanParam;
import com.study.projects.newphotoproject.model.param.PlanBuyParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server-plans") //admin
@RequiredArgsConstructor
public class ServerPlanController {
    private final ServerPlanFacade serverPlanFacade;

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<ServerPlanDetailDto>> getDetailedServerPlans() {
        return new ResponseEntity<>(serverPlanFacade.getAllServerPlans(), HttpStatus.OK);
    }
    @PostMapping("/add-plan")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ServerPlanDetailDto> addServerPlan(@RequestBody AddServerPlanParam addServerPlanParam) {
        return new ResponseEntity<>(serverPlanFacade.addServerPlan(addServerPlanParam), HttpStatus.CREATED);
    }

    @PatchMapping("/{serverPlanId}/modify-plan")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ServerPlanDetailDto> changeServerPlan(@PathVariable Long serverPlanId, @RequestBody ModifyServerPlanParam modifyServerPlanParam) {
        return new ResponseEntity<>(serverPlanFacade.modifyServerPlan(serverPlanId ,modifyServerPlanParam), HttpStatus.OK);
    }

    @DeleteMapping("/{serverPlanId}/invalidate")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ServerPlanDetailDto> invalidateServerPlanById(@PathVariable Long serverPlanId) {
        return new ResponseEntity<>(serverPlanFacade.invalidateServerPlan(serverPlanId), HttpStatus.OK);
    }

    @PatchMapping("/{serverPlanId}/activate")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ServerPlanDetailDto> activateServerPlanById(@PathVariable Long serverPlanId) {
        return new ResponseEntity<>(serverPlanFacade.activateServerPlan(serverPlanId), HttpStatus.OK);
    }
    @PostMapping("/{userId}/buy-plan")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserServerDto> buyServerPlan(@PathVariable Long userId, @RequestBody PlanBuyParam planBuyParam) {
        return new ResponseEntity<>(serverPlanFacade.buyPlan(userId, planBuyParam),HttpStatus.OK);
    }

}
