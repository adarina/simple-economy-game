package com.ada.simpleeconomygamespringboot.user.repository;

import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureTestDatabase
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUserRepository_whenSaveAndRetrieveUser_thenReturnsOK() {
        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("ADMIN")
                .buildUserEntity();
        User testUserWithId = userRepository.save(testUser);
        Optional<User> foundUser = userRepository.findById(testUserWithId.getId());

        assertNotNull(foundUser);
        if (foundUser.isPresent()) {
            assertEquals(testUser.getUsername(), foundUser.get().getUsername());
            assertEquals(testUser.getPassword(), foundUser.get().getPassword());
        }
    }

    @Test
    public void givenUserWithoutUsername_whenSave_thenReturnsError() {
        User testUser = UserBuilder
                .anUser()
                .withPassword("tester")
                .withRole("ADMIN")
                .buildUserEntity();
        Assertions.assertThrows(javax.validation.ConstraintViolationException.class,
                () -> userRepository.save(testUser));
    }

    @Test
    public void givenUserWithoutPassword_whenSave_thenReturnsError() {
        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withRole("ADMIN")
                .buildUserEntity();
        Assertions.assertThrows(javax.validation.ConstraintViolationException.class,
                () -> userRepository.save(testUser));
    }

    @Test
    public void givenUserWithoutRole_whenSave_thenReturnsError() {
        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .buildUserEntity();
        Assertions.assertThrows(javax.validation.ConstraintViolationException.class,
                () -> userRepository.save(testUser));
    }
}
