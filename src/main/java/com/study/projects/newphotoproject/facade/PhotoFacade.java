package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.*;
import com.study.projects.newphotoproject.model.dto.PhotoDetailDto;
import com.study.projects.newphotoproject.model.dto.PhotoDto;
import com.study.projects.newphotoproject.model.enums.*;
import com.study.projects.newphotoproject.model.mapper.PhotoMapper;
import com.study.projects.newphotoproject.model.param.AddPhotoParam;
import com.study.projects.newphotoproject.model.param.ModifyPhotoParam;
import com.study.projects.newphotoproject.model.param.MoveCopyPhotoParam;
import com.study.projects.newphotoproject.service.*;
import com.study.projects.newphotoproject.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        Long principalId = AuthService.getPrincipalDatabaseId();

        UserEntity principal = userService.findByUserId(principalId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (principal.getUserStatus() == UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User deactivated!");
    }

    @Transactional
    public List<PhotoDto> getAllPhotos(Long albumId, Pageable pageable) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        AlbumEntity album = albumService.findAlbumById(albumId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));

        UserServerEntity userServer = album.getServerEntity();

        if(userValidator.checkIfAdmin() || userValidator.checkOwnership(userServer.getServerUserEntity().getId()) || hasReadModifyAccess(albumId, principalId)) {

            if (userServer.getServerUserEntity().getUserStatus() == UserStatus.DEACTIVATED)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Owner deactivated!");
            else if (album.getAlbumStatus() == AlbumStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album deleted!");
            else return photoService.getPhotosByAlbumId(albumId, pageable).stream().filter(p-> p.getPhotoStatus() == PhotoStatus.ACTIVE).map(PhotoMapper::toPhotoDto).toList();

        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + album.getAlbumName() + "!");

    }

    @Transactional
    public PhotoDetailDto getPhotoById(Long albumId, Long photoId) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        PhotoEntity photo = photoService.findByPhotoId(photoId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found!"));
        Long albumOwnerId = photo.getPhotoAlbumEntity().getServerEntity().getServerUserEntity().getId();

        if (userValidator.checkIfAdmin() || userValidator.checkOwnership(albumOwnerId) || hasReadModifyAccess(albumId, principalId)) {

            if (albumId != photo.getPhotoAlbumEntity().getAlbumId())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong album!");
            else if (photo.getPhotoAlbumEntity().getServerEntity().getServerUserEntity().getUserStatus() == UserStatus.DEACTIVATED)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Owner deactivated!");
            else if (photo.getPhotoStatus() == PhotoStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo deleted!");
            else return PhotoMapper.toPhotoDetailDto(photo);

        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + photo.getPhotoAlbumEntity().getAlbumName() + "!");

    }

    @Transactional
    public PhotoDetailDto addPhoto(Long albumId, AddPhotoParam addPhotoParam) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        //PostAuthorize(MODIFY only) and check user activation
        AlbumEntity album = albumService.findAlbumById(albumId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
        UserServerEntity userServer = userServerService.getUserServerByAlbumId(albumId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User server not found!"));

        if (userValidator.checkIfAdmin() || userValidator.checkOwnership(userServer.getServerUserEntity().getId()) || hasModifyAccess(albumId, principalId)) {

            if (userServer.getServerUserEntity().getUserStatus() == UserStatus.DEACTIVATED)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Owner deactivated!");
            else if (album.getAlbumStatus() == AlbumStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album deleted!");
            else {

                double photoSize = addPhotoParam.getPhotoSize();
                double afterCapacity = userServer.getUsedCapacity() + photoSize;
                double maxCapacity = userServer.getServerPlanEntity().getServerPlanCapacity();

                if (afterCapacity > maxCapacity)
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not enough storage!");
                else {

                    List<PhotoEntity> sameNamePhotos = album.getPhotos().stream()
                            .filter(p-> p.getPhotoName().equals(addPhotoParam.getPhotoName()) && p.getPhotoStatus() == PhotoStatus.ACTIVE).toList();

                    if (!sameNamePhotos.isEmpty())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo with the same name already exists!");

                    PhotoEntity photo = new PhotoEntity();
                    photo.setPhotoName(addPhotoParam.getPhotoName());
                    photo.setPhotoUri(addPhotoParam.getPhotoUri());
                    photo.setPhotoSize(photoSize);
                    photo.setPhotoAlbumEntity(album);
                    photo.setPhotoStatus(PhotoStatus.ACTIVE);
                    photoService.savePhoto(photo);


                    userServer.setUsedCapacity(userServer.getUsedCapacity()+photoSize);
                    userServerService.saveUserServer(userServer);

                    return PhotoMapper.toPhotoDetailDto(photo);

                }

            }

        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access this album!");

    }

    @Transactional
    public PhotoDetailDto modifyPhoto(ModifyPhotoParam modifyPhotoParam) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        PhotoEntity photo = photoService.findByPhotoId(modifyPhotoParam.getPhotoId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo deleted!"));
        Long albumOwnerId = photo.getPhotoAlbumEntity().getServerEntity().getServerUserEntity().getId();

        Long albumId = photo.getPhotoAlbumEntity().getAlbumId();

        if (userValidator.checkIfAdmin() || userValidator.checkOwnership(albumOwnerId) || hasModifyAccess(albumId, principalId)) {

            if (photo.getPhotoStatus()== PhotoStatus.USERDEACTIVATED)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Owner deactivated!");
            else if (photo.getPhotoStatus() == PhotoStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo deleted!");
            else {

                List<PhotoEntity> sameNamePhotos = photo.getPhotoAlbumEntity().getPhotos().stream()
                                .filter(p -> p.getPhotoName().equals(modifyPhotoParam.getPhotoName()) && p.getPhotoStatus() == PhotoStatus.ACTIVE).toList();

                if (!sameNamePhotos.isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo with the same name already exists!");

                photo.setPhotoName(modifyPhotoParam.getPhotoName());
                photoService.savePhoto(photo);

                return PhotoMapper.toPhotoDetailDto(photo);

            }

        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + photo.getPhotoAlbumEntity().getAlbumName() + "!");

    }

    @Transactional
    public PhotoDetailDto copyPhoto(Long albumId,MoveCopyPhotoParam moveCopyPhotoParam) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        AlbumEntity fromAlbum = albumService.findAlbumById(albumId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
        AlbumEntity toAlbum = albumService.findAlbumById(moveCopyPhotoParam.getToAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
        PhotoEntity photo = photoService.findByPhotoId(moveCopyPhotoParam.getPhotoId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found!"));

        Long fromAlbumId = fromAlbum.getAlbumId();
        Long toAlbumId = toAlbum.getAlbumId();

        UserServerEntity userServer = userServerService.getUserServerByAlbumId(toAlbum.getAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User server not found!"));

        if (fromAlbum.getAlbumId().equals(toAlbum.getAlbumId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same album!");
        } else {

            if (userValidator.checkIfAdmin() || userValidator.checkOwnership(fromAlbum.getAlbumId()) || hasReadModifyAccess(fromAlbumId, principalId)) {

                if (userValidator.checkIfAdmin() || userValidator.checkOwnership(userServer.getServerUserEntity().getId()) || hasModifyAccess(toAlbumId, principalId)) {

                    if (fromAlbum.getAlbumStatus() == AlbumStatus.USERDEACTIVATED)
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, fromAlbum.getAlbumName() + "'s owner is deactivated!");
                    else if (toAlbum.getAlbumStatus() == AlbumStatus.USERDEACTIVATED)
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, toAlbum.getAlbumName() + "'s owner is deactivated!");
                    else {

                        if (fromAlbum.getAlbumStatus() == AlbumStatus.DELETED)
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, fromAlbum.getAlbumName() + " deleted!");
                        else if (toAlbum.getAlbumStatus() == AlbumStatus.DELETED)
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, toAlbum.getAlbumName() + " deleted!");
                        else if (photo.getPhotoStatus()==PhotoStatus.DELETED)
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo deleted!");
                        else {
                            List<PhotoEntity> samNamePhotos = toAlbum.getPhotos().stream()
                                    .filter(p-> p.getPhotoName().equals(photo.getPhotoName()) && p.getPhotoStatus() != PhotoStatus.ACTIVE).toList();

                            if (!samNamePhotos.isEmpty())
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo with the same name already exists!");

                            double afterCapacity = userServer.getUsedCapacity()+photo.getPhotoSize();
                            double maxCapacity = userServer.getServerPlanEntity().getServerPlanCapacity();

                            if (afterCapacity > maxCapacity)
                                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not enough storage!");

                            PhotoEntity newPhoto = new PhotoEntity();
                            newPhoto.setPhotoName(photo.getPhotoName());
                            newPhoto.setPhotoUri(photo.getPhotoUri());
                            newPhoto.setPhotoSize(photo.getPhotoSize());
                            newPhoto.setPhotoAlbumEntity(toAlbum);
                            newPhoto.setPhotoStatus(PhotoStatus.ACTIVE);
                            photoService.savePhoto(newPhoto);

                            userServer.setUsedCapacity(afterCapacity);
                            userServerService.saveUserServer(userServer);

                            return PhotoMapper.toPhotoDetailDto(newPhoto);
                        }

                    }

                } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + toAlbum.getAlbumName() + "!");

            }
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + fromAlbum.getAlbumName() + "!");

        }

    }

    @Transactional
    public PhotoDetailDto movePhoto(Long albumId, MoveCopyPhotoParam moveCopyPhotoParam) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        AlbumEntity fromAlbum = albumService.findAlbumById(albumId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
        AlbumEntity toAlbum = albumService.findAlbumById(moveCopyPhotoParam.getToAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
        PhotoEntity photo = photoService.findByPhotoId(moveCopyPhotoParam.getPhotoId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found!"));

        Long fromAlbumId = fromAlbum.getAlbumId();
        Long toAlbumId = toAlbum.getAlbumId();

        UserServerEntity fromUserServer = userServerService.getUserServerByAlbumId(fromAlbum.getAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User server not found!"));
        UserServerEntity toUserServer = userServerService.getUserServerByAlbumId(toAlbum.getAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User server not found!"));

        if (fromAlbum.getAlbumId().equals(toAlbum.getAlbumId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same album!");
        } else {

            if (userValidator.checkIfAdmin() || userValidator.checkOwnership(fromAlbum.getAlbumId()) || hasModifyAccess(fromAlbumId, principalId)) {

                if (userValidator.checkIfAdmin() || userValidator.checkOwnership(toUserServer.getServerUserEntity().getId()) || hasModifyAccess(toAlbumId, principalId)) {

                    if (fromAlbum.getAlbumStatus() == AlbumStatus.USERDEACTIVATED)
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, fromAlbum.getAlbumName() + "'s owner is deactivated!");
                    else if (toAlbum.getAlbumStatus() == AlbumStatus.USERDEACTIVATED)
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, toAlbum.getAlbumName() + "'s owner is deactivated!");
                    else {

                        if (fromAlbum.getAlbumStatus() == AlbumStatus.DELETED)
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, fromAlbum.getAlbumName() + " deleted!");
                        else if (toAlbum.getAlbumStatus() == AlbumStatus.DELETED)
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, toAlbum.getAlbumName() + " deleted!");
                        else if (photo.getPhotoStatus()==PhotoStatus.DELETED)
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo deleted!");
                        else {
                            List<PhotoEntity> samNamePhotos = toAlbum.getPhotos().stream()
                                    .filter(p-> p.getPhotoName().equals(photo.getPhotoName()) && p.getPhotoStatus() != PhotoStatus.ACTIVE).toList();

                            if (!samNamePhotos.isEmpty())
                                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo with the same name already exists!");

                            double afterCapacity = toUserServer.getUsedCapacity()+photo.getPhotoSize();
                            double maxCapacity = toUserServer.getServerPlanEntity().getServerPlanCapacity();
                            double photoSize = photo.getPhotoSize();

                            if (afterCapacity > maxCapacity)
                                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not enough storage!");

                            photo.setPhotoAlbumEntity(toAlbum);
                            photoService.savePhoto(photo);

                            fromUserServer.setUsedCapacity(fromUserServer.getUsedCapacity()-photoSize);
                            toUserServer.setUsedCapacity(afterCapacity);

                            userServerService.saveUserServer(fromUserServer);
                            userServerService.saveUserServer(toUserServer);

                            return PhotoMapper.toPhotoDetailDto(photo);
                        }

                    }

                } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + toAlbum.getAlbumName() + "!");

            }
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + fromAlbum.getAlbumName() + "!");

        }


    }

    @Transactional
    public void deletePhoto(Long photoId) {

        Long principalId = AuthService.getPrincipalDatabaseId();

        isPrincipalActivated();

        PhotoEntity photo = photoService.findByPhotoId(photoId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found!"));
        AlbumEntity album = photo.getPhotoAlbumEntity();

        Long albumId = album.getAlbumId();

        UserServerEntity userServer = userServerService.getUserServerByAlbumId(album.getAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User server not found!"));

        if (userValidator.checkIfAdmin() || userValidator.checkOwnership(userServer.getServerUserEntity().getId()) || hasModifyAccess(albumId, principalId)) {

            if (userServer.getServerUserEntity().getUserStatus() == UserStatus.DEACTIVATED)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, album.getAlbumName() + "'s owner deactivated!");
            else if (album.getAlbumStatus() == AlbumStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, album.getAlbumName() + " deleted!");
            else if (photo.getPhotoStatus()==PhotoStatus.DELETED)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found!");

            photo.setPhotoStatus(PhotoStatus.DELETED);
            photoService.savePhoto(photo);

            userServer.setUsedCapacity(userServer.getUsedCapacity()-photo.getPhotoSize());
            userServerService.saveUserServer(userServer);


        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access " + album.getAlbumName() + "!");

    }

    @Transactional
    public boolean hasReadModifyAccess(Long albumId, Long toUserId) {

        Pageable all = PageRequest.of(0, Integer.MAX_VALUE);

        List<ShareRightsEntity> readShareRights = sharingService.getShareRightsByAlbumAndToUser(albumId, toUserId, all).stream()
                .filter(s-> (s.getShareRightType() == ShareRightType.READ || s.getShareRightType() == ShareRightType.MODIFY) && s.getShareRightstatus() == ShareRightStatus.VALID).toList();

        if (!readShareRights.isEmpty()) {
            if (readShareRights.size() > 1)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Multiple read rights found!");
            else return true;
        }
        else return false;
    }

    @Transactional
    public boolean hasModifyAccess(Long albumId, Long toUserId) {
        Pageable all = PageRequest.of(0, Integer.MAX_VALUE);

        List<ShareRightsEntity> readShareRights = sharingService.getShareRightsByAlbumAndToUser(albumId, toUserId, all).stream()
                .filter(s-> s.getShareRightType() == ShareRightType.MODIFY && s.getShareRightstatus() == ShareRightStatus.VALID).toList();

        if (!readShareRights.isEmpty()) {
            if (readShareRights.size() > 1)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Multiple read rights found!");
            else return true;
        }
        else return false;
    }

}
