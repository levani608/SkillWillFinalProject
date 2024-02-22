package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.PhotoDetailDto;
import com.study.projects.newphotoproject.model.dto.PhotoDto;
import com.study.projects.newphotoproject.model.param.AddPhotoParam;
import com.study.projects.newphotoproject.model.param.ModifyPhotoParam;
import com.study.projects.newphotoproject.model.param.MoveCopyPhotoParam;
import com.study.projects.newphotoproject.service.*;
import com.study.projects.newphotoproject.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoFacade {

    private final PhotoService photoService;
    private final AlbumService albumService;
    private final UserService userService;
    private final SharingService sharingService;
    private final UserServerService userServerService;

    private final UserValidator userValidator;

    private void isPrincipalActivated() {
        return;
    }

    @Transactional
    public List<PhotoDto> getAllPhotos(Long albumId, Pageable pageable) {

        return null;
    }

    @Transactional
    public PhotoDetailDto getPhotoById(Long albumId, Long photoId) {
        return null;
    }

    @Transactional
    public PhotoDetailDto addPhoto(Long albumId, AddPhotoParam addPhotoParam) {

        return null;
    }

    @Transactional
    public PhotoDetailDto modifyPhoto(ModifyPhotoParam modifyPhotoParam) {
        return null;
    }

    @Transactional
    public PhotoDetailDto copyPhoto(Long albumId,MoveCopyPhotoParam moveCopyPhotoParam) {

        return null;

    }

    @Transactional
    public PhotoDetailDto movePhoto(Long albumId, MoveCopyPhotoParam moveCopyPhotoParam) {

        return null;


    }

    @Transactional
    public void deletePhoto(Long photoId) {

        return;
    }

    @Transactional
    public boolean hasReadModifyAccess(Long albumId, Long toUserId) {

        return false;
    }

    @Transactional
    public boolean hasModifyAccess(Long albumId, Long toUserId) {
        return false;
    }

}
