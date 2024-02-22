package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.SharingFacade;
import com.study.projects.newphotoproject.model.dto.ShareRightsDto;
import com.study.projects.newphotoproject.model.param.AddShareParam;
import com.study.projects.newphotoproject.model.param.ModifyShareParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{userId}/shares")
@RequiredArgsConstructor
public class SharingController {

    private final SharingFacade sharingFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<ShareRightsDto>> getAllShares(@PathVariable Long userId) {
        return new ResponseEntity<>(sharingFacade.getAllShares(userId), HttpStatus.OK);
    }

    @PostMapping("/add-sharing")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ShareRightsDto> shareAlbumToUser(@PathVariable Long userId, @RequestBody AddShareParam addShareParam) {
        return new ResponseEntity<>(sharingFacade.addSharing(userId, addShareParam), HttpStatus.CREATED);
    }

    @PutMapping("/modify-sharing")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ShareRightsDto> modifySharingRight(@PathVariable Long userId, @RequestParam("shareId") Long shareId, @RequestBody ModifyShareParam modifyShareParam) {
        return new ResponseEntity<>(sharingFacade.modifySharingRight(userId, shareId, modifyShareParam), HttpStatus.OK);
    }

    @DeleteMapping("/remove-sharing-right")
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<ShareRightsDto> removeSharingRight(@PathVariable Long userId, @RequestParam ("share_id")Long shareId) {
        return new ResponseEntity<>(sharingFacade.removeSharingRight(userId, shareId), HttpStatus.OK);
    }

}
