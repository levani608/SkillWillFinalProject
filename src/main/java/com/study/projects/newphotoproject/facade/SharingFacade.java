package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.domain.database.ShareRightsEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.ShareRightsDto;
import com.study.projects.newphotoproject.model.enums.AlbumStatus;
import com.study.projects.newphotoproject.model.enums.ShareRightStatus;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.mapper.ShareRightMapper;
import com.study.projects.newphotoproject.model.param.AddShareParam;
import com.study.projects.newphotoproject.model.param.ModifyShareParam;
import com.study.projects.newphotoproject.service.AlbumService;
import com.study.projects.newphotoproject.service.SharingService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharingFacade {

    private final SharingService sharingService;
    private final UserService userService;
    private final AlbumService albumService;
    //private final UserValidator userValidator;

    @Transactional
    public List<ShareRightsDto> getAllShares(Long userId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus() == UserStatus.DEACTIVATED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User deactivated!");
        }

        return user.getToUsers().stream().filter(s-> s.getShareRightstatus() == ShareRightStatus.VALID).map(ShareRightMapper::toShareRightsDto).toList();
    }

    @Transactional
    public ShareRightsDto addSharing(Long userId, AddShareParam addShareParam) {

        UserEntity fromUser = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        UserEntity toUser = userService.findByUserId(addShareParam.getToUserId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (fromUser.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Host user deactivated!");
        else if (toUser.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "To user deactivated!");


        AlbumEntity sharedAlbum = albumService.findAlbumById(addShareParam.getSharedAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));

        if (sharedAlbum.getAlbumStatus() == AlbumStatus.DELETED)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album deleted!");
        else if (sharedAlbum.getServerEntity().getServerUserEntity().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this album!");


        ShareRightsEntity shareRightsEntity = new ShareRightsEntity();
        List<ShareRightsEntity> existingShareRights = sharedAlbum.getSharedTo().stream() //haringService.findExistingShareRights(userId, addModifyShareParam.getToUserId(), addModifyShareParam.getSharedAlbumId());
                .filter(sr-> sr.getToUserEntity().getId() == addShareParam.getToUserId() && sr.getShareRightstatus()== ShareRightStatus.VALID).toList();

        if (!existingShareRights.isEmpty()) {
            /*for (ShareRightsEntity e : existingShareRights) {
                if (e.getShareRightstatus()== ShareRightStatus.VALID) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already shared this album to this user!");
                }
            }*/
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already shared this album to this user!");
        }
        else {
            shareRightsEntity.setFromUserEntity(fromUser);
            shareRightsEntity.setToUserEntity(toUser);
            shareRightsEntity.setSharedAlbumEntity(sharedAlbum);
            shareRightsEntity.setShareRightType(addShareParam.getShareRightType());
            shareRightsEntity.setShareRightstatus(ShareRightStatus.VALID);
            sharingService.saveShareRight(shareRightsEntity);
        }

        return ShareRightMapper.toShareRightsDto(shareRightsEntity);
    }

    @Transactional
    public ShareRightsDto modifySharingRight(Long userId, Long shareId, ModifyShareParam modifyShareParam) {

        UserEntity fromUser = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        UserEntity toUser = userService.findByUserId(modifyShareParam.getToUserId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (fromUser.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Host user deactivated!");
        else if (toUser.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "To user deactivated!");

        ShareRightsEntity shareRight = sharingService.getShareRightById(shareId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Share right not found!"));
        if (shareRight.getFromUserEntity().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this share!");
        else if (shareRight.getShareRightstatus() != ShareRightStatus.VALID)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Share not found!");

        AlbumEntity oldSharedAlbum = shareRight.getSharedAlbumEntity();
        AlbumEntity newSharedAlbum = albumService.findAlbumById(modifyShareParam.getNewSharedAlbumId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found!"));
        if (oldSharedAlbum.getAlbumStatus() == AlbumStatus.DELETED || newSharedAlbum.getAlbumStatus() == AlbumStatus.DELETED)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album deleted!");
        else if (newSharedAlbum.getServerEntity().getServerUserEntity().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this album!");

        List<ShareRightsEntity> newAlbumShares = newSharedAlbum.getSharedTo().stream().
                filter(sr-> sr.getToUserEntity().getId() == modifyShareParam.getToUserId() && sr.getShareRightType() == modifyShareParam.getShareRightType() && sr.getShareRightstatus()== ShareRightStatus.VALID).toList();

        if (!newAlbumShares.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already shared this album to this user!");

        shareRight.setToUserEntity(toUser);
        shareRight.setSharedAlbumEntity(newSharedAlbum);
        shareRight.setShareRightType(modifyShareParam.getShareRightType());

        sharingService.saveShareRight(shareRight);

        return ShareRightMapper.toShareRightsDto(shareRight);
    }

    @Transactional
    public ShareRightsDto removeSharingRight(Long userId, Long shareId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus()== UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User deactivated!");

        ShareRightsEntity shareRight = sharingService.getShareRightById(shareId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Share right not found!"));
        if (shareRight.getFromUserEntity().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this share!");
        else if (shareRight.getShareRightstatus() != ShareRightStatus.VALID)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no share right to delete!");

        shareRight.setShareRightstatus(ShareRightStatus.INVALID);

        sharingService.saveShareRight(shareRight);

        return ShareRightMapper.toShareRightsDto(shareRight);
    }
}



