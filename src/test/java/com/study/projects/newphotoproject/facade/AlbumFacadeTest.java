package com.study.projects.newphotoproject.facade;


import com.study.projects.newphotoproject.model.domain.database.AlbumEntity;
import com.study.projects.newphotoproject.model.domain.database.ServerPlanEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserServerEntity;
import com.study.projects.newphotoproject.model.dto.AlbumDto;
import com.study.projects.newphotoproject.model.enums.AlbumStatus;
import com.study.projects.newphotoproject.model.enums.UserServerStatus;
import com.study.projects.newphotoproject.model.param.AddAlbumParam;
import com.study.projects.newphotoproject.service.*;
import com.study.projects.newphotoproject.util.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AlbumFacadeTest {
    @Mock
    private AlbumService albumService;
    @Mock
    private UserServerService userServerService;
    @Mock
    private UserService userService;
    @Mock
    private UserValidator userValidator;
    @Mock
    private SharingService sharingService;

    private AlbumFacade albumFacade;

    @BeforeEach
    void setUp() {
        albumFacade = new AlbumFacade(albumService, userServerService, userService, userValidator, sharingService);
    }

    @Test
    void testGetAlbums_shouldThrowExceptionWhenUserIsDeactivated() {
        mockGetPrincipalUserId(1L);
        Mockito.when(userServerService.getUserServer(1L)).thenReturn(Optional.of(createUserServerEntity(UserServerStatus.USERDEACTIVATED, 1L)));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> albumFacade.getAlbums(1L));
        Assertions.assertEquals(exception.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void testGetAlbums_shouldThrowExceptionWhenUserDoesNotOwnAlbumOrIsNotAdmin() {
        mockGetPrincipalUserId(1L);
        Mockito.when(userServerService.getUserServer(1L)).thenReturn(Optional.of(createUserServerEntity(UserServerStatus.ACTIVE, 3L)));
        Mockito.when(userValidator.checkIfAdmin()).thenReturn(false);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> albumFacade.getAlbums(1L));
        Assertions.assertEquals(exception.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        Assertions.assertEquals("406 NOT_ACCEPTABLE \"You have no access to this server!\"", exception.getMessage());
    }

    @Test
    void testGetAlbums_shouldReturnCorrectResult() {
        mockGetPrincipalUserId(1L);
        Mockito.when(userServerService.getUserServer(1L)).thenReturn(Optional.of(createUserServerEntity(UserServerStatus.ACTIVE, 1L)));
        Mockito.when(userValidator.checkIfAdmin()).thenReturn(false);
        List<AlbumEntity> album = List.of(createAlbum(AlbumStatus.ACTIVE, "AlbumActive"), createAlbum(AlbumStatus.DELETED, "AlbumDeleted"));
        Mockito.when(albumService.getAlbums(1L)).thenReturn(album);
        List<AlbumDto> albums = albumFacade.getAlbums(1L);
        Assertions.assertEquals(1, albums.size());
        Assertions.assertEquals("AlbumActive", albums.get(0).getAlbumName());
    }

    @Test
    void testAddAlbum_shouldReturnCorrectResult() {
        mockGetPrincipalUserId(1L);
        UserServerEntity userServerEntity = createUserServerEntity(UserServerStatus.ACTIVE, 1L);
        AlbumEntity album1 = createAlbum(AlbumStatus.ACTIVE, "Album1");
        AlbumEntity album2 = createAlbum(AlbumStatus.ACTIVE, "Album2");
        userServerEntity.setAlbums(Set.of(album1, album2));
        Mockito.when(userServerService.getUserServer(1L)).thenReturn(Optional.of(userServerEntity));

        AddAlbumParam addAlbumParam = new AddAlbumParam();
        addAlbumParam.setAlbumName("Album3");
        AlbumDto albumDto = albumFacade.addAlbum(1L, addAlbumParam);
        Assertions.assertEquals("Album3", albumDto.getAlbumName());
        Assertions.assertEquals(1L, albumDto.getUserServerDto().getUserServerId());

    }

    @Test
    void testDeleteAlbum_shouldReturnCorrectResult(){
        mockGetPrincipalUserId(1L);
        UserServerEntity userServerEntity = createUserServerEntity(UserServerStatus.ACTIVE, 1L);
        AlbumEntity album1 = createAlbum(AlbumStatus.ACTIVE, "Album1");
        userServerEntity.setAlbums(Set.of(album1));
        album1.setServerEntity(userServerEntity);

        Mockito.when(albumService.findAlbumById(1L)).thenReturn(Optional.of(album1));

         albumFacade.deleteAlbum(1L);

        ArgumentCaptor<AlbumEntity> albumEntityArgumentCaptor = ArgumentCaptor.forClass(AlbumEntity.class);
        Mockito.verify(albumService).saveAlbum(albumEntityArgumentCaptor.capture());
        Assertions.assertEquals(AlbumStatus.DELETED,albumEntityArgumentCaptor.getValue().getAlbumStatus());

    }

    @Test
    void testGetAllAlbumsByUserId_shouldReturnCorrectResult(){

    }

    private AlbumEntity createAlbum(AlbumStatus status, String albumName) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumName(albumName);
        albumEntity.setAlbumStatus(status);
        return albumEntity;
    }

    private UserServerEntity createUserServerEntity(UserServerStatus status, Long userId) {
        UserEntity userEntity = new UserEntity();
        UserServerEntity userServer = new UserServerEntity();
        ServerPlanEntity serverPlanEntity = new ServerPlanEntity();
        serverPlanEntity.setServerPlanName("Drive1");
        userServer.setUserServerStatus(status);
        userServer.setUserServerId(1L);
        userEntity.setId(userId);
        userServer.setServerPlanEntity(serverPlanEntity);
        userServer.setServerUserEntity(userEntity);
        return userServer;
    }

    private void mockGetPrincipalUserId(Long id) {
        MockedStatic<AuthService> authServiceMockedStatic = Mockito.mockStatic(AuthService.class);
        authServiceMockedStatic.when(AuthService::getPrincipalDatabaseId).thenReturn(id);
    }


}
