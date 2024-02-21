package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.ShareRightsEntity;
import com.study.projects.newphotoproject.model.dto.ShareRightsDto;

public class ShareRightMapper {

    public static ShareRightsDto toShareRightsDto(ShareRightsEntity shareRightsEntity) {
        return new ShareRightsDto(shareRightsEntity.getFromUserEntity().getUsername(), shareRightsEntity.getToUserEntity().getUsername(),
                    shareRightsEntity.getSharedAlbumEntity().getAlbumName(), shareRightsEntity.getShareRightType(), shareRightsEntity.getUpdatedAt());
    }

}
