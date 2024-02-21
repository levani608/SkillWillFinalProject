package com.study.projects.newphotoproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PhotoDetailDto extends PhotoDto{

    private String albumName;

    private Double photoSize;

    private LocalDate addTime;

    private LocalDate updateTime;

    public PhotoDetailDto(String photoName, String albumName, String photoUri, Double photoSize, LocalDate addTime, LocalDate updateTime) {
        this.photoName=photoName;
        this.albumName=albumName;
        this.photoUri=photoUri;
        this.photoSize=photoSize;
        this.addTime=addTime;
        this.updateTime=updateTime;
    }

}
