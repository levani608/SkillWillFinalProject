package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.ShareRightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ShareRightsDto {

    private String fromUserName;

    private String toUserName;

    private String sharedAlbumName;

    private ShareRightType shareRightType;

    private LocalDate shareDate;

}
