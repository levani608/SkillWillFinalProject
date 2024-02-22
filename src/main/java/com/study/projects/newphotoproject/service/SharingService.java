package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.ShareRightsEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.repository.SharingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SharingService {

    private final SharingRepository sharingRepository;


    public List<ShareRightsEntity> findAllShareByUserId(Long userId) {
        return sharingRepository.findAllByFromUserEntityId(userId);
    }

    public List<ShareRightsEntity> findExistingShareRights(Long userId, Long toUserId, Long sharedAlbumId) {
        return sharingRepository.findAllByFromUserEntityIdAndToUserEntityIdAndSharedAlbumEntityAlbumId(userId, toUserId, sharedAlbumId);
    }

    @Transactional
    public ShareRightsEntity saveShareRight(ShareRightsEntity shareRightsEntity) {
        return sharingRepository.saveAndFlush(shareRightsEntity);
    }

    @Transactional
    public List<ShareRightsEntity> getSharingRightsByUser(UserEntity user) {
        return sharingRepository.findAllByToUserEntity(user);
    }

    @Transactional
    public Optional<ShareRightsEntity> getShareRightById(Long shareId) {
        return sharingRepository.findById(shareId);
    }

    @Transactional
    public Page<ShareRightsEntity> getShareRightsByAlbumAndToUser(Long albumId, Long userId, Pageable pageable) {

        return sharingRepository.findAllByAlbumAndToUser(albumId, userId, pageable);
    }
}
