package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.dto.AlbumDto;

public class AlbumMapper {

    public static AlbumDto toAlbumDto(AlbumEntity albumEntity) {

        return new AlbumDto(albumEntity.getAlbumName(), UserServerMapper.toUserServerDto(albumEntity.getServerEntity()));
    }

}
