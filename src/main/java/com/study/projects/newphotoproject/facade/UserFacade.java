package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.mapper.UserMapper;
import com.study.projects.newphotoproject.model.param.AddMoneyParam;
import com.study.projects.newphotoproject.model.param.filterparams.UserFilterParam;
import com.study.projects.newphotoproject.service.AuthService;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;



    public Page<UserDetailDto> getFilteredUsers(UserFilterParam userFilterParam) {

        return userService.getFilteredUsers(userFilterParam).map(UserMapper::toUserDetailDto);
    }

    public UserDetailDto getUserById(Long userId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        return UserMapper.toUserDetailDto(user);
    }

    public void addMoneyOnUsersBalance(AddMoneyParam addMoneyParam) {
        UserEntity userEntity = userService.findByUserId(AuthService.getPrincipalDatabaseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!;"));
        userEntity.setBalance(userEntity.getBalance()+ addMoneyParam.getAmount());
        userService.saveUser(userEntity);
    }
}
