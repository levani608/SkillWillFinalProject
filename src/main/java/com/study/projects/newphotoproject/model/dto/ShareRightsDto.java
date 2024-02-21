package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.ShareRightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ShareRightsDto {

    private String fromUserName;

    private String toUserName;

    private String sharedAlbumName;

    @Enumerated(EnumType.STRING)
    private ShareRightType shareRightType;

    private LocalDate shareDate;

}
