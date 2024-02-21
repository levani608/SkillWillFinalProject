package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {


    Optional<AlbumEntity> findById(Long id);

    List<AlbumEntity> findAllByServerEntityUserServerId(Long userServerId);

    List<AlbumEntity> findAllByServerEntityServerUserEntity(UserEntity user, Pageable pageable);

}
