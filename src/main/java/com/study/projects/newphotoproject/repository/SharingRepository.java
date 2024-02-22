package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.ShareRightsEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharingRepository extends JpaRepository<ShareRightsEntity, Long> {

        List<ShareRightsEntity> findAllByFromUserEntityId(Long userId);
        List<ShareRightsEntity> findAllByFromUserEntityIdAndToUserEntityIdAndSharedAlbumEntityAlbumId(Long userId, Long toUserId, Long sharedAlbumId);
        List<ShareRightsEntity> findAllByToUserEntity(UserEntity userEntity);

        @Query("select s from share_rights s where s.sharedAlbumEntity.albumId = :albumId and s.toUserEntity.id = :userId")
        Page<ShareRightsEntity> findAllByAlbumAndToUser(Long albumId, Long userId, Pageable pageable);

}
