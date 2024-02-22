package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.AlbumFacade;
import com.study.projects.newphotoproject.model.dto.AlbumDto;
import com.study.projects.newphotoproject.model.param.AddAlbumParam;
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
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumFacade albumFacade;

    @GetMapping("/{userId}/all-albums")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    public ResponseEntity<List<AlbumDto>> getAllAlbumsByUserId(@PathVariable Long userId,
                                                               @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                               @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                               @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
                                                               @RequestParam(value = "sort", defaultValue = "id", required = false) String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return new ResponseEntity<>(albumFacade.getAllAlbumsByUserId(userId, pageable),HttpStatus.OK);
    }

    @GetMapping("/{userId}/shared-albums")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    @PreAuthorize("hasRole('ADMIN') or @UserValidator.checkOwnership(#userId)")
    public ResponseEntity<List<AlbumDto>> getAllSharedAlbums(@PathVariable Long userId) {
        return new ResponseEntity<>(albumFacade.getAllSharedAlbums(userId),HttpStatus.OK);
    }

    @GetMapping("/{userServerId}/server-albums")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<AlbumDto>> getAlbums(@PathVariable Long userServerId) {



        return new ResponseEntity<>(albumFacade.getAlbums(userServerId),HttpStatus.OK);
    }

    @PostMapping("/{userServerId}/add-album") //userId or admin
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<AlbumDto> addAlbum(@PathVariable Long userServerId, @RequestBody AddAlbumParam addAlbumParam) {
        return new ResponseEntity<>(albumFacade.addAlbum(userServerId, addAlbumParam),HttpStatus.CREATED);
    }

    @DeleteMapping("{albumId}/delete") //userId, admin
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<AlbumDto> deleteAlbumById(@PathVariable Long albumId) {
        return new ResponseEntity<>(albumFacade.deleteAlbum(albumId),HttpStatus.OK);
    }

}
