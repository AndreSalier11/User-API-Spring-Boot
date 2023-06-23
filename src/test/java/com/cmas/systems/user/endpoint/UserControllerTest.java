package com.cmas.systems.user.endpoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cmas.systems.user.domain.User;
import com.cmas.systems.user.domain.assembler.UserModelAssembler;
import com.cmas.systems.user.domain.models.UserModel;
import com.cmas.systems.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserModelAssembler userModelAssembler;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        userController.setUserService(userService);
        userController.setUserModelAssembler(userModelAssembler);
    }

    @Test
    public void testFindAll() {

        // Given
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "André"));
        given(userService.findAll()).willReturn(userList);

        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(new UserModel(1L, "André"));
        given(userModelAssembler.toModel(any(User.class))).willReturn(new UserModel(1L, "André"));

        // When
        ResponseEntity<List<UserModel>> responseEntity = userController.findAll();

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(userModelList);
    }

    @Test
    public void testFindById() {

        // Given
        User user = new User(1L, "André");
        given(userService.findById(1L)).willReturn(user);

        UserModel userModel = new UserModel(1L, "André");
        given(userModelAssembler.toModel(user)).willReturn(userModel);

        // When
        ResponseEntity<UserModel> responseEntity = userController.findById(1L);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(userModel);
    }

    @Test
    public void testInsert() {

        // Given
        User user = new User(null, "André");
        given(userService.insert(user)).willReturn(new User(1L, "André"));

        UserModel userModel = new UserModel(1L, "André");
        given(userModelAssembler.toModel(any(User.class))).willReturn(new UserModel(1L, "André"));

        // When
        ResponseEntity<UserModel> responseEntity = userController.insert(user);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(userModel);
    }

    @Test
    public void testDelete() {

        // Given
        User user = new User(1L, "André");
        given(userService.findById(1L)).willReturn(user);

        // When
        ResponseEntity<User> responseEntity = userController.delete(1L);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(user);

        verify(userService).delete(1L);
    }

    @Test
    @SuppressWarnings("null")
    public void testUpdate() {

        // Given
        User user = new User(null, "André");
        given(userService.update(any(User.class))).willReturn(new User(1L, "André"));

        given(userModelAssembler.toModel(any(User.class))).willReturn(new UserModel(1L, "André"));

        // When
        ResponseEntity<UserModel> responseEntity = userController.update(user, 1L);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isNotNull().isEqualTo(1L);
    }
}
