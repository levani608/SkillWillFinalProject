package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.UserFacade;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.param.AddMoneyParam;
import com.study.projects.newphotoproject.model.param.filterparams.UserFilterParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;


    @GetMapping("/{userId}") //userId or admin
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserDetailDto> getUserDetailDto(@PathVariable Long userId) {
        return new ResponseEntity<>(userFacade.getUserById(userId), HttpStatus.OK);
    }
    @GetMapping //admin
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<Page<UserDetailDto>> getFilteredUsers(UserFilterParam userFilterParam) {
        return new ResponseEntity<>(userFacade.getFilteredUsers(userFilterParam), HttpStatus.OK);
    }

    @PutMapping
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<Void> addMoneyOnUsersBalance(@RequestBody AddMoneyParam addMoneyParam){
        userFacade.addMoneyOnUsersBalance(addMoneyParam);
        return ResponseEntity.ok().build();
    }

}
