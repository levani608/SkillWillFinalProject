package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.param.filterparams.UserFilterParam;
import com.study.projects.newphotoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.study.projects.newphotoproject.model.enums.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    public UserEntity saveUserInDatabase(UserEntity user) {
        return userRepository.save(user);
    }



    public UserEntity findByUserName(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User or password is incorrect;"));
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.saveAndFlush(user);
    }
    public Page<UserEntity> getFilteredUsers(UserFilterParam userFilterParam) {


        return userRepository.findAllByFilter(
                userFilterParam.getUsername(),
                List.of(ACTIVE),
                PageRequest.of(userFilterParam.getPage(),userFilterParam.getSize())
        );
    }

}
