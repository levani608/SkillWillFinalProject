package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Transactional
    public List<AlbumEntity> getAlbums(Long userServerId) {
        return albumRepository.findAllByServerEntityUserServerId(userServerId);
    }

    @Transactional
    public AlbumEntity saveAlbum(AlbumEntity album) {
        return albumRepository.saveAndFlush(album);
    }

    @Transactional
    public Optional<AlbumEntity> findAlbumById(Long albumId) {
        return albumRepository.findById(albumId);
    }


    @Transactional
    public Page<AlbumEntity> getAlbumsByUser(Long userId, Pageable pageable) {
        return albumRepository.findAllByServerEntityServerUserEntity(userId, pageable);
    }
}
