package com.study.projects.newphotoproject.controller;

import com.study.projects.newphotoproject.facade.PhotoFacade;
import com.study.projects.newphotoproject.model.param.AddPhotoParam;
import com.study.projects.newphotoproject.model.param.ModifyPhotoParam;
import com.study.projects.newphotoproject.model.dto.PhotoDetailDto;
import com.study.projects.newphotoproject.model.dto.PhotoDto;
import com.study.projects.newphotoproject.model.param.MoveCopyPhotoParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{albumId}/photos") //userId, sharedToUserId, admin
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoFacade photoFacade;

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<List<PhotoDto>> getAllPhotos(@PathVariable Long albumId,
                                                       @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                       @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                       @RequestParam(value = "direction", defaultValue = "ASC", required = false) Sort.Direction direction,
                                                       @RequestParam(value = "sort", defaultValue = "id", required = false) String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        return new ResponseEntity<>(photoFacade.getAllPhotos(albumId, pageable), HttpStatus.OK);
    }

    @GetMapping("/photo")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<PhotoDetailDto> getPhotoById(@PathVariable Long albumId, @RequestParam("photo_id") Long photoId) {
        return new ResponseEntity<>(photoFacade.getPhotoById(albumId, photoId), HttpStatus.OK);
    }

    @PostMapping("/add-photo")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<PhotoDetailDto> addPhoto(@PathVariable Long albumId, @RequestBody AddPhotoParam addPhotoParam) {
        return new ResponseEntity<>(photoFacade.addPhoto(albumId, addPhotoParam), HttpStatus.CREATED);
    }

    @PatchMapping("/modify")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<PhotoDetailDto> modifyPhoto( @RequestBody ModifyPhotoParam modifyPhotoParam) {
        return new ResponseEntity<>(photoFacade.modifyPhoto(modifyPhotoParam), HttpStatus.OK);
    }

    @PostMapping("/copy")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<PhotoDetailDto> copyPhoto(@PathVariable Long albumId, @RequestBody MoveCopyPhotoParam moveCopyPhotoParam) {
        return new ResponseEntity<>(photoFacade.copyPhoto(albumId,moveCopyPhotoParam), HttpStatus.CREATED);
    }

    @PostMapping("/move")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<PhotoDetailDto> movePhoto(@PathVariable Long albumId, @RequestBody MoveCopyPhotoParam moveCopyPhotoParam) {
        return new ResponseEntity<>(photoFacade.movePhoto(albumId,moveCopyPhotoParam), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<Void> deletePhoto( @RequestParam("photo_id") Long photoId) {
        photoFacade.deletePhoto(photoId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
