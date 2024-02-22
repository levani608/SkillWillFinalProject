package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.param.AddMoneyParam;
import com.study.projects.newphotoproject.model.param.filterparams.UserFilterParam;
import com.study.projects.newphotoproject.service.AuthService;
import com.study.projects.newphotoproject.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UserFacadeTest {

    @Mock
    private UserService userService;

    private UserFacade userFacade;

    @BeforeEach
    void setUp() {
        userFacade=new UserFacade(userService);
    }

    @Test
    void testGetFilteredUsers_shouldReturnCorrectResult() {
        UserFilterParam userFilterParam = createUserFilterParam();
        List<UserEntity> userEntities = new ArrayList<>();
        PageImpl<UserEntity> userEntityPage = new PageImpl<>(userEntities);
        Mockito.when(userService.getFilteredUsers(userFilterParam)).thenReturn(userEntityPage);

        Page<UserDetailDto> filteredUsers = userFacade.getFilteredUsers(userFilterParam);
        List<UserDetailDto> content = filteredUsers.getContent();
        UserEntity userEntity = userEntities.get(0);
        UserDetailDto userDetailDto = content.get(0);
        Assertions.assertEquals(userEntity.getId(), userDetailDto.getUserId());
        Assertions.assertEquals(userEntity.getUsername(), userDetailDto.getUsername());
        Assertions.assertEquals(userEntity.getFirstName(), userDetailDto.getFirstName());
    }

    @Test
    void testGetUserById() {
//when

        //actual
        UserDetailDto userById = userFacade.getUserById(1L);
        //assertions
        Assertions.assertEquals(1L,userById.getUserId());

    }

    @Test
    void testAddMoneyOnBalance_shouldReturnCorrectResult(){
        UserEntity userEntity = new UserEntity();
        userEntity.setBalance(15.0);
        Mockito.when(userService.findByUserId(1L))
                .thenReturn(Optional.of(userEntity));
        MockedStatic<AuthService> authServiceMockedStatic = Mockito.mockStatic(AuthService.class);
        authServiceMockedStatic.when(AuthService::getPrincipalDatabaseId).thenReturn(1L);

        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        userFacade.addMoneyOnUsersBalance(new AddMoneyParam(5.0));
        Mockito.verify(userService).saveUser(userEntityArgumentCaptor.capture());
        UserEntity capture = userEntityArgumentCaptor.getValue();
        Assertions.assertEquals(20.0,capture.getBalance());
    }

    private UserFilterParam createUserFilterParam() {
        return new UserFilterParam("something", 1, 2);
    }
}
