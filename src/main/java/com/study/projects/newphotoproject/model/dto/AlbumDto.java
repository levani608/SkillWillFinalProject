package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class AlbumDto {

    private String albumName;
    private UserServerDto userServerDto;

}
