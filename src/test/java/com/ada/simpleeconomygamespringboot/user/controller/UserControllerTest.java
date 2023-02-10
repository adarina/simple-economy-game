package com.ada.simpleeconomygamespringboot.user.controller;

import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private UnitService unitService;

    @Test
    public void givenUser_whenGetUser_thenReturnsUserAndOK() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        when(userService.find(1L)).thenReturn(Optional.ofNullable(testUser));

        mvc.perform(get("/api/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.username").value("tester"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUser_whenGetAllUsers_thenReturnsUserListAndOK() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("ADMIN")
                .buildUserEntity();

        List<User> userList = new ArrayList<>();
        userList.add(testUser);

        when(userService.findAll()).thenReturn(userList);
        mvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.users").exists())
                .andExpect(jsonPath("$.users[*].username").value("tester"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUser_whenAddNewUser_thenReturnsIsCreated() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        User testUserWithId = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUserWithId, 1L);

        when(userService.create(testUser)).thenReturn(testUserWithId);

        System.out.println(objectMapper.writeValueAsString(testUser));
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated());
    }
}
