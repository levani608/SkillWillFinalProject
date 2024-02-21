package com.study.projects.newphotoproject.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddPhotoParam {

    private String photoName;

    private String photoUri;

    private Double photoSize;

}
