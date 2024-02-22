package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.param.AddMoneyParam;
import com.study.projects.newphotoproject.model.param.filterparams.UserFilterParam;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;



    public Page<UserDetailDto> getFilteredUsers(UserFilterParam userFilterParam) {

        return null;
    }

    public UserDetailDto getUserById(Long userId) {
        return null;
    }

    public void addMoneyOnUsersBalance(AddMoneyParam addMoneyParam) {
        return;
    }
}
