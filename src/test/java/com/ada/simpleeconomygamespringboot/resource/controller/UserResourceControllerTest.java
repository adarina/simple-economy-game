package com.ada.simpleeconomygamespringboot.resource.controller;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResourceController.class)
public class UserResourceControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ResourceService resourceService;


    @Test
    public void givenUserAndResources_whenGetResources_thenReturnsResourcesAndOK() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("user", testUser);

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Resource testResourceStone = ResourceBuilder
                .aResource()
                .defaultBuildStoneEntity(testUser);

        Resource testResourceMeat = ResourceBuilder
                .aResource()
                .defaultBuildMeatEntity(testUser);

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResourceMud);
        testResources.add(testResourceStone);
        testResources.add(testResourceMeat);
        testUser.setResources(testResources);

        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(resourceService.findAll(testUser)).thenReturn(testResources);

        mvc.perform(get("/api/users/1/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resources").isNotEmpty())
                .andExpect(jsonPath("$.resources[0].type").value("MUD"))
                .andExpect(jsonPath("$.resources[0].amount").value(4000000))
                .andExpect(jsonPath("$.resources[1].type").value("STONE"))
                .andExpect(jsonPath("$.resources[1].amount").value(1000000))
                .andExpect(jsonPath("$.resources[2].type").value("MEAT"))
                .andExpect(jsonPath("$.resources[2].amount").value(1000000));
    }
}
