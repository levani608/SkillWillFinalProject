package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.domain.database.PhotoEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {


    List<PhotoEntity> findAllByPhotoAlbumEntityServerEntityServerUserEntity(UserEntity user);

    List<PhotoEntity> findAllByPhotoAlbumEntity(AlbumEntity album);

    @Query("select p from photos p where p.photoAlbumEntity.albumId = :albumId")
    List<PhotoEntity> findAllBYPhotoAlbumEntityAlbumId(Long albumId, Pageable pageable);

}
