package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.UserServerFacade;
import com.study.projects.newphotoproject.model.dto.UserServerByPlanNameDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{userId}/user-servers")
@RequiredArgsConstructor
public class UserServerController {

    private final UserServerFacade userServerFacade;

    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<UserServerDto>> getUserServers(@PathVariable Long userId) {

        return new ResponseEntity<>(userServerFacade.getUserServers(userId), HttpStatus.OK);
    }


    @PostMapping("/get-free-server")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserServerDto> getFreeServer(@PathVariable Long userId) {
        return new ResponseEntity<>(userServerFacade.getFreeServer(userId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @GetMapping ("/{serverPlanName}")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserServerByPlanNameDto> getUserServersByPlanName(@PathVariable Long userId, @PathVariable String serverPlanName) {
        return new ResponseEntity<>(userServerFacade.getUserServersByPlanName(userId, serverPlanName), HttpStatus.OK);
    }

}
