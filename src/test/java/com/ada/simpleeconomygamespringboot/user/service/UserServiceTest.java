package com.ada.simpleeconomygamespringboot.user.service;

import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void givenUser_whenFindUserById_thenReturnsUser() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        Optional<User> expectedResult = Optional.of(user);

        when(userRepository.findById(id)).thenReturn(expectedResult);

        Optional<User> result = userService.find(id);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void givenUsername_whenFindUserByUsername_thenReturnsUser() {
        String username = "tester";
        User user = new User();
        user.setUsername(username);
        Optional<User> expectedResult = Optional.of(user);

        when(userRepository.findByUsername(username)).thenReturn(expectedResult);

        Optional<User> result = userService.find(username);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void whenFindAllUsers_thenReturnsListOfUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertThat(result).isEqualTo(users);
    }

    @Test
    public void givenUser_whenCreateNewUser_thenReturnsCreatedUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.create(user);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void givenId_whenDeleteUser_thenUserIsDeleted() {
        Long id = 1L;

        userService.delete(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    public void givenUsernameAndPassword_whenFindUserByUsernameAndPassword_thenReturnsUser() {

        String username = "tester";
        String password = "tester";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(user);

        User result = userService.findByUsernameAndPassword(username, password);

        assertThat(result).isEqualTo(user);
    }
}

