package com.ada.simpleeconomygamespringboot.building.controller;


import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.building.entity.BuildingBuilder;
import com.ada.simpleeconomygamespringboot.building.service.BuildingService;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(UserBuildingController.class)
public class UserBuildingControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BuildingService buildingService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    public void givenUserAndBuildings_whenGetBuildings_thenReturnsBuildingsAndOK() throws Exception {

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

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        Building testBuildingGoblinsCavern = BuildingBuilder
                .aBuilding()
                .defaultBuildGoblinsCavernEntity(testUser);

        List<Building> testBuildings = new ArrayList<>();
        testBuildings.add(testBuildingMudGatherersCottage);
        testBuildings.add(testBuildingGoblinsCavern);
        testUser.setBuildings(testBuildings);

        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(buildingService.findAll(testUser)).thenReturn(testBuildings);

        mvc.perform(get("/api/users/1/buildings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buildings").isNotEmpty())
                .andExpect(jsonPath("$.buildings[0].type").value(testBuildings.get(0).getType()))
                .andExpect(jsonPath("$.buildings[1].type").value(testBuildings.get(1).getType()));
    }

    @Test
    public void givenUserAndBuilding_whenPostBuilding_thenReturnsOK() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("user", testUser);

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        privateIdField = Building.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testBuildingMudGatherersCottage, 1L);

        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(buildingService.canBuild(any(), any())).thenReturn(true);
        when(buildingService.create(any())).thenReturn(testBuildingMudGatherersCottage);

        mvc.perform(post("/api/users/1/buildings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
                        .content(objectMapper.writeValueAsString(testBuildingMudGatherersCottage))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenNoExistedUserId_whenPostBuilding_thenReturnsNotFound() throws  Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("user", testUser);

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(null);

        when(userService.find(1L)).thenReturn(Optional.empty());

        mvc.perform(post("/api/users/2/buildings")
                .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
                        .content(objectMapper.writeValueAsString(testBuildingMudGatherersCottage))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUserAndBuilding_whenGetBuilding_thenReturnsBuildingAndOK() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("user", testUser);

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        List<Building> testBuildings = new ArrayList<>();
        testBuildings.add(testBuildingMudGatherersCottage);
        testUser.setBuildings(testBuildings);


        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(buildingService.find(1L, 1L)).thenReturn(Optional.ofNullable(testBuildingMudGatherersCottage));

        assert testBuildingMudGatherersCottage != null;
        mvc.perform(get("/api/users/1/buildings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").isNotEmpty())
                .andExpect(jsonPath("$.type").value(testBuildingMudGatherersCottage.getType()));
    }

    @Test
    public void givenWrongRequest_whenGetBuilding_thenReturnsNotFound() throws Exception {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("user", testUser);

        mvc.perform(get("/api/users/1/buildings/3")
                        .contentType(MediaType.APPLICATION_JSON)
                .session(mockSession))
                .andExpect(status().isNotFound());
    }
}
