package com.cmas.systems.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cmas.systems.user.domain.User;
import com.cmas.systems.user.exceptions.SomeFieldNotFilledException;
import com.cmas.systems.user.exceptions.UserNotFoundException;
import com.cmas.systems.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    private void SetUp() {
        userService = new UserService();
        userService.setRepo(userRepository);
    }

    @Test
    public void testFindAll() {

        // Given
        User user = new User(1L, "André");

        given(userRepository.findAll()).willReturn(List.of(user));

        // When
        List<User> response = userService.findAll();

        // Then
        assertThat(response).isEqualTo(List.of(user));
    }

    @Test
    public void testFindById() {

        // Given
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // When
        User response = userService.findById(userId);

        // Then
        assertThat(response).isEqualTo(user);
    }

    @Test
    public void testFindByNonExistingId() {

        // Given
        Long userId = 2L;
        given(userRepository.findById(userId)).willThrow(UserNotFoundException.class);
        Exception exception = null;

        // When
        try {
            userRepository.findById(userId);
        } catch (UserNotFoundException e) {
            exception = e;
        }

        // Then
        assertThat(exception).isNotNull();
    }

    @Test
    public void testInsert() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Given
        User user4 = new User(null, "Carlos", "Santana", "carlos.santana@email.com", sdf.parse("18/04/2005"), false);
        given(userRepository.save(user4)).willReturn(user4);

        // When
        User response = userService.insert(user4);

        // Then
        assertThat(response).isEqualTo(user4);
    }

    @Test
    public void testInsertEmpty() {

        // Given
        User user4 = new User(null, null, null, null, null, null);
        Exception exception = null;

        // When
        try {
            userService.insert(user4);
        } catch (SomeFieldNotFilledException e) {
            exception = e;
        }

        // Then
        assertThat(exception).isNotNull();
    }

    @Test
    public void testDeleteNonExistingId() {

        // Given
        Long userId = 2L;
        given(userRepository.findById(any())).willThrow(UserNotFoundException.class);

        // When
        Exception exception = null;
        try {
            userService.delete(userId);
        } catch (UserNotFoundException e) {
            exception = e;
        }

        // Then
        assertThat(exception).isNotNull();
    }

    @Test
    public void testDelete() {

        // Given
        Long userId = 2L;
        User user = new User();
        user.setId(userId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // When
        Exception exception = null;
        try {
            userService.delete(userId);
        } catch (UserNotFoundException e) {
            exception = e;
        }

        // Then
        assertThat(exception).isNull();
    }

    @Test
    public void testUpdate() {

        // Given
        Long userId = 2L;

        User oldUser = new User(userId, "André");
        User newUser = new User(userId, "Luís");

        given(userRepository.findById(userId)).willReturn(Optional.of(oldUser));
        given(userRepository.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // When
        User user = userService.update(newUser);

        // Then
        assertThat(user.getFirstName()).isEqualTo(newUser.getFirstName());
    }
}
