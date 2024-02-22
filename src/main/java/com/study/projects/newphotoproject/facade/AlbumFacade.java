package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.AlbumDto;
import com.study.projects.newphotoproject.model.param.AddAlbumParam;
import com.study.projects.newphotoproject.service.AlbumService;
import com.study.projects.newphotoproject.service.SharingService;
import com.study.projects.newphotoproject.service.UserServerService;
import com.study.projects.newphotoproject.service.UserService;
import com.study.projects.newphotoproject.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumFacade {

    private final AlbumService albumService;
    private final UserServerService userServerService;
    private final UserService userService;
    private final UserValidator userValidator;
    private final SharingService sharingService;


    @Transactional
    public List<AlbumDto> getAlbums(Long userServerId) {

        return null;

    }

    public AlbumDto addAlbum(Long userServerId, AddAlbumParam addAlbumParam) {

        return null;

    }


    @Transactional
    public AlbumDto deleteAlbum(Long albumId) {

        return null;

    }

    @Transactional
    public List<AlbumDto> getAllAlbumsByUserId(Long userId, Pageable pageable) {

        return null;
    }

    @Transactional
    public List<AlbumDto> getAllSharedAlbums(Long userId) {
        return null;
    }
}

