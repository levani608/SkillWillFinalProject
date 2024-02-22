package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.PhotoEntity;
import com.study.projects.newphotoproject.model.dto.PhotoDetailDto;
import com.study.projects.newphotoproject.model.dto.PhotoDto;

public class PhotoMapper {

    public static PhotoDto toPhotoDto(PhotoEntity photoEntity) {
        return new PhotoDto(photoEntity.getPhotoId(),photoEntity.getPhotoName(), photoEntity.getPhotoUri());
    }

    public static PhotoDetailDto toPhotoDetailDto(PhotoEntity photoEntity) {
        return new PhotoDetailDto(photoEntity.getPhotoName(), photoEntity.getPhotoAlbumEntity().getAlbumName(), photoEntity.getPhotoUri(),
                photoEntity.getPhotoSize(), photoEntity.getCreatedAt(), photoEntity.getUpdatedAt());
    }

}
