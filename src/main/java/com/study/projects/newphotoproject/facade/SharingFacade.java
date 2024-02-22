package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.ShareRightsDto;
import com.study.projects.newphotoproject.model.param.AddShareParam;
import com.study.projects.newphotoproject.model.param.ModifyShareParam;
import com.study.projects.newphotoproject.service.AlbumService;
import com.study.projects.newphotoproject.service.SharingService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharingFacade {

    private final SharingService sharingService;
    private final UserService userService;
    private final AlbumService albumService;
    //private final UserValidator userValidator;

    @Transactional
    public List<ShareRightsDto> getAllShares(Long userId) {

        return null;
    }

    @Transactional
    public ShareRightsDto addSharing(Long userId, AddShareParam addShareParam) {
        return null;
    }

    @Transactional
    public ShareRightsDto modifySharingRight(Long userId, Long shareId, ModifyShareParam modifyShareParam) {

        return null;
    }

    @Transactional
    public ShareRightsDto removeSharingRight(Long userId, Long shareId) {

        return null;
    }
}



