package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {



    List<AlbumEntity> findAllByServerEntityUserServerId(Long userServerId);

    @Query("""
    select a from AlbumEntity a
    where a.serverEntity.serverUserEntity.id = :userId
""")
    Page<AlbumEntity> findAllByServerEntityServerUserEntity(Long userId, Pageable pageable);

}
