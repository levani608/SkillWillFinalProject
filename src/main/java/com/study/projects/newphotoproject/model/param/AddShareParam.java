package com.study.projects.newphotoproject.model.param;

import com.study.projects.newphotoproject.model.enums.ShareRightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
public class AddShareParam {

    private Long toUserId;

    private Long sharedAlbumId;

    @Enumerated(EnumType.STRING)
    private ShareRightType shareRightType;

}
