package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import com.study.projects.newphotoproject.model.enums.UserServerStatus;
import com.study.projects.newphotoproject.repository.UserServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServerService {

    private final UserServerRepository userserverRepository;


    public List<UserServerEntity> saveUserServers(List<UserServerEntity> userServers) {
        return userserverRepository.saveAllAndFlush(userServers);
    }

    public Optional<UserServerEntity> getUserServer(Long userServerId) {
        return userserverRepository.findById(userServerId);
    }

    public List<UserServerEntity> getUserServersByUserId(Long userId) {
        return userserverRepository.findAllByServerUserEntityId(userId);
    }

    @Transactional
    public Optional<UserServerEntity> getUserServerByAlbumId(Long albumId) {
        return userserverRepository.findByAlbumId(albumId);
    }

    @Transactional
    public UserServerEntity saveUserServer(UserServerEntity userServer) {
        return userserverRepository.saveAndFlush(userServer);
    }

    public void userDeactivate(UserEntity user) {
        List<UserServerEntity> userServers = userserverRepository.findAllByServerUserEntity(user).stream()
                .filter(us-> us.getUserServerStatus() == UserServerStatus.ACTIVE).toList();

        for (UserServerEntity userServer : userServers) {
            userServer.setUserServerStatus(UserServerStatus.USERDEACTIVATED);
        }

        userserverRepository.saveAllAndFlush(userServers);
    }

    public void userActivate(UserEntity user) {
        List<UserServerEntity> userServers = userserverRepository.findAllByServerUserEntity(user).stream()
                .filter(us-> us.getUserServerStatus() == UserServerStatus.USERDEACTIVATED).toList();

        for (UserServerEntity userServer : userServers) {
            userServer.setUserServerStatus(UserServerStatus.ACTIVE);
        }

        userserverRepository.saveAllAndFlush(userServers);
    }

    public Optional<UserServerEntity> getUserServerById(Long serverId) {
        return userserverRepository.findById(serverId);
    }
}
