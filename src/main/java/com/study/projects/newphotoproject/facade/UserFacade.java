package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.mapper.UserMapper;
import com.study.projects.newphotoproject.model.param.filterparams.UserFilterParam;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;


    public List<UserDetailDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(UserMapper::toUserDetailDto).toList();
    }

    public Page<UserDetailDto> getFilteredUsers(UserFilterParam userFilterParam) {

        return userService.getFilteredUsers(userFilterParam).map(UserMapper::toUserDetailDto);
    }

    public UserDetailDto getUserById(Long userId) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        return UserMapper.toUserDetailDto(user);
    }

}
