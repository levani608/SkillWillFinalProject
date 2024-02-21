package com.study.projects.newphotoproject.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RemoveShareParam {

    private Long toUserId;

    private Long sharedAlbumId;

}
