package com.study.projects.newphotoproject.model.param;

import com.study.projects.newphotoproject.model.enums.ShareRightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyShareParam {

    private Long toUserId;

    private Long newSharedAlbumId;

    private ShareRightType shareRightType;

}
