package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.domain.database.PhotoEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.PhotoDto;
import com.study.projects.newphotoproject.model.enums.PhotoStatus;
import com.study.projects.newphotoproject.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Transactional
    public Optional<PhotoEntity> findByPhotoId(Long photoId) {
        return photoRepository.findById(photoId);
    }

    @Transactional
    public PhotoEntity savePhoto(PhotoEntity photo) {
        return photoRepository.saveAndFlush(photo);
    }

    public void userDeactivate(UserEntity user) {
        List<PhotoEntity> photos = photoRepository.findAllByPhotoAlbumEntityServerEntityServerUserEntity(user).stream()
                .filter(p-> p.getPhotoStatus()== PhotoStatus.ACTIVE).toList();

        for (PhotoEntity photo : photos) {
            photo.setPhotoStatus(PhotoStatus.USERDEACTIVATED);
        }

        photoRepository.saveAllAndFlush(photos);
    }

    public void userActivate(UserEntity user) {
        List<PhotoEntity> photos = photoRepository.findAllByPhotoAlbumEntityServerEntityServerUserEntity(user).stream()
                .filter(p-> p.getPhotoStatus()== PhotoStatus.USERDEACTIVATED).toList();

        for (PhotoEntity photo : photos) {
            photo.setPhotoStatus(PhotoStatus.ACTIVE);
        }

        photoRepository.saveAllAndFlush(photos);
    }

    public void deleteAlbumPhotos(AlbumEntity album) {
        List<PhotoEntity> photos = photoRepository.findAllByPhotoAlbumEntity(album).stream()
                .filter(p-> p.getPhotoStatus()==PhotoStatus.ACTIVE).toList();

        for (PhotoEntity photo : photos) {
            photo.setPhotoStatus(PhotoStatus.DELETED);
        }

        photoRepository.saveAllAndFlush(photos);
    }


    public List<PhotoEntity> getPhotosByAlbumId(Long albumId, Pageable pageable) {
        return photoRepository.findAllBYPhotoAlbumEntityAlbumId(albumId, pageable);
    }
}
