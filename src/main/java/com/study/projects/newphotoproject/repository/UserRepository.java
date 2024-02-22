package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findUserByUsername(String username);
    

    @Query(value ="""
                  select u from UserEntity u 
                  where 
                  (u.username is null or u.username like concat('%',:username,'%') ) 
                  and u.userStatus in (:userStatuses)
                  """)
    Page<UserEntity> findAllByFilter(
            @Param("username") String username,
            @Param("userStatuses") List<UserStatus> userStatuses,
            Pageable pageable
    );
}
