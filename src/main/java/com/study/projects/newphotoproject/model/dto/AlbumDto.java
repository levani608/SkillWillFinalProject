package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class AlbumDto {

    private String albumName;
    private UserServerDto userServerDto;

}
