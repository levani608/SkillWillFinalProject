package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserServerRepository extends JpaRepository<UserServerEntity, Long> {

    @Query("""
        select us from UserServerEntity us inner join albums a on us.userServerId = a.serverEntity.userServerId where
        a.albumId = :albumId
""")
    Optional<UserServerEntity> findByAlbumId(Long albumId);

    @Query("""
        select us from UserServerEntity us
        where us.serverUserEntity.id =:userId
""")
    List<UserServerEntity> findAllByServerUserEntityId(Long userId);

    List<UserServerEntity> findAllByServerUserEntity(UserEntity user);

}
