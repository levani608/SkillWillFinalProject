package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.UserWalletFacade;
import com.study.projects.newphotoproject.model.dto.UserWalletDetailDto;
import com.study.projects.newphotoproject.model.dto.UserWalletDto;
import com.study.projects.newphotoproject.model.dto.WalletTransactionDto;
import com.study.projects.newphotoproject.model.param.AddWalletParam;
import com.study.projects.newphotoproject.model.param.FillUserWalletParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photo-project/wallets")
@RequiredArgsConstructor
public class UserWalletController { //userId or admin

    private final UserWalletFacade userWalletFacade;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserWalletDto> getAllUserWallets(@PathVariable Long userId) {
        return new ResponseEntity<>(userWalletFacade.getActiveUserWallet(userId), HttpStatus.OK);
    }


    @GetMapping("/{userWalletId}/get-transactions")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<WalletTransactionDto>> getAllWalletTransactions(/*@PathVariable Long userId,*/ @PathVariable Long userWalletId) {
        return new ResponseEntity<>(userWalletFacade.getAllWalletTransactions(userWalletId),HttpStatus.OK);
    }

    @PostMapping("/{userId}/add-wallet")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserWalletDetailDto> addWallet(@PathVariable Long userId, @RequestBody AddWalletParam addWalletParam) {
        return new ResponseEntity<>(userWalletFacade.addWallet(userId, addWalletParam),HttpStatus.CREATED);
    }

    @PatchMapping("/fill-balance")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserWalletDto> fillWalletBalance( @RequestBody FillUserWalletParam fillUserWalletParam) {
        return new ResponseEntity<>(userWalletFacade.fillWalletBalance(fillUserWalletParam),HttpStatus.OK);
    }

    @DeleteMapping("/{userWalletId}/delete")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<UserWalletDetailDto> deleteWallet( @PathVariable Long userWalletId) {
        return new ResponseEntity<>(userWalletFacade.deleteWallet(userWalletId),HttpStatus.OK);
    }

}
