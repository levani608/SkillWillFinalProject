package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.domain.database.ShareRightsEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import com.study.projects.newphotoproject.model.dto.AlbumDto;
import com.study.projects.newphotoproject.model.enums.AlbumStatus;
import com.study.projects.newphotoproject.model.enums.ShareRightStatus;
import com.study.projects.newphotoproject.model.enums.UserServerStatus;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.mapper.AlbumMapper;
import com.study.projects.newphotoproject.model.param.AddAlbumParam;
import com.study.projects.newphotoproject.service.*;
import com.study.projects.newphotoproject.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumFacade {

    private final AlbumService albumService;
    private final UserServerService userServerService;
    private final UserService userService;
    private final UserValidator userValidator;
    private final SharingService sharingService;
    private final AuthService authService;

    @Transactional
    public List<AlbumDto> getAlbums(Long userServerId) {

        Long principalId = authService.getPrincipalDatabaseId();

        UserServerEntity userServer = userServerService.getUserServer(userServerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server not found!"));

        if (userServer.getUserServerStatus()== UserServerStatus.USERDEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");
        else if (userValidator.checkIfAdmin() || userServer.getServerUserEntity().getId() == principalId) {
            List<AlbumEntity> allAlbums = albumService.getAlbums(userServerId).stream()
                    .filter(a-> a.getAlbumStatus()== AlbumStatus.ACTIVE).toList();

            return allAlbums.stream().map(AlbumMapper::toAlbumDto).toList();
        }
        else throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You have no access to this server!");

    }

    public AlbumDto addAlbum(Long userServerId, AddAlbumParam addAlbumParam) {

        Long principalId = authService.getPrincipalDatabaseId();

        UserServerEntity userServer = userServerService.getUserServer(userServerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server not found!"));
        if (userServer.getServerUserEntity().getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User deactivated!");
        else if (userValidator.checkIfAdmin() || userServer.getServerUserEntity().getId() == principalId) {

            List<AlbumEntity> sameNameAlbums = userServer.getAlbums().stream()
                    .filter(a-> a.getAlbumName().equals(addAlbumParam.getAlbumName()) && a.getAlbumStatus()== AlbumStatus.ACTIVE).toList();
            if (!sameNameAlbums.isEmpty())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Album with this name already exists!");

            AlbumEntity album = new AlbumEntity();
            album.setAlbumName(addAlbumParam.getAlbumName());
            album.setServerEntity(userServer);
            album.setAlbumStatus(AlbumStatus.ACTIVE);
            albumService.saveAlbum(album);

            return AlbumMapper.toAlbumDto(album);
        }else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have no access to this server!");

    }


    @Transactional
    public AlbumDto deleteAlbum(Long albumId) {

        Long principalId = authService.getPrincipalDatabaseId();

        AlbumEntity album = albumService.findAlbumById(albumId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));

        if (album.getServerEntity().getServerUserEntity().getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");
        else if (userValidator.checkIfAdmin() || album.getServerEntity().getServerUserEntity().getId() == principalId) {

            if (album.getAlbumStatus()== AlbumStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Album already deleted!");

            album.setAlbumStatus(AlbumStatus.DELETED);
            albumService.saveAlbum(album);

            //photoService.deleteAlbumPhotos(album);

            return AlbumMapper.toAlbumDto(album);
        }
        else throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You have no access to this album!");

    }

    @Transactional
    public List<AlbumDto> getAllAlbumsByUserId(Long userId, Pageable pageable) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus() == UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");

        List<AlbumEntity> allAlbums = albumService.getAlbumsByUser(user, pageable).stream()
                .filter(a-> a.getAlbumStatus()== AlbumStatus.ACTIVE).toList();

        return allAlbums.stream().map(AlbumMapper::toAlbumDto).toList();
    }

    @Transactional
    public List<AlbumDto> getAllSharedAlbums(Long userId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus() == UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");

        List<ShareRightsEntity> validShareRights = sharingService.getSharingRightsByUser(user).stream()
                .filter(sr-> sr.getShareRightstatus() == ShareRightStatus.VALID).toList();

        List<AlbumEntity> allAlbums = new ArrayList<>();

        for (ShareRightsEntity v : validShareRights) {
            allAlbums.add(v.getSharedAlbumEntity());
        }

        return allAlbums.stream().filter(a-> a.getAlbumStatus() == AlbumStatus.ACTIVE).map(AlbumMapper::toAlbumDto).toList();
    }
}

