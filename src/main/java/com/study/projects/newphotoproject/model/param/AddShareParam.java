package com.study.projects.newphotoproject.model.param;

import com.study.projects.newphotoproject.model.enums.ShareRightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class AddShareParam {

    private Long toUserId;

    private Long sharedAlbumId;

    private ShareRightType shareRightType;

}
