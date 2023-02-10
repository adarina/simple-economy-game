package com.ada.simpleeconomygamespringboot.unit.controller;

import com.ada.simpleeconomygamespringboot.unit.dto.GetUnitsResponse;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserUnitController.class)
public class UserUnitControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UnitService unitService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UnitRepository unitRepository;


    @Test
    public void givenUserAndUnits_whenGetUnits_thenReturnsUnitsAndOK() throws Exception {

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

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        Unit testUnitOrcWarrior = UnitBuilder
                .anUnit()
                .defaultBuildOrcWarriorEntity(testUser);

        Unit testIUnitUglyTroll = UnitBuilder
                .anUnit()
                .defaultBuildUglyTrollEntity(testUser);


        List<Unit> testUnits = new ArrayList<>();
        testUnits.add(testUnitGoblinArcher);
        testUnits.add(testIUnitUglyTroll);
        testUnits.add(testUnitOrcWarrior);

        testUser.setUnits(testUnits);

        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(unitService.findAll(testUser)).thenReturn(testUnits);

        mvc.perform(get("/api/users/1/units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.units").isNotEmpty())
                .andExpect(jsonPath("$.units[0].type").value("GOBLIN"))
                .andExpect(jsonPath("$.units[1].type").value("TROLL"))
                .andExpect(jsonPath("$.units[2].type").value("ORC"));
    }


    @Test
    public void givenUserToGoblinArcherInUnit_whenUpdateUnit_thenReturnsAccepted() throws Exception {

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

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        privateIdField = Unit.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUnitGoblinArcher, 1L);

        when(unitService.find(1L, 1L)).thenReturn(Optional.of(testUnitGoblinArcher));
        when(unitService.canRecruit(any(),any(), any())).thenReturn(true);
        when(unitService.update(any())).thenReturn(testUnitGoblinArcher);

        mvc.perform(put("/api/users/1/units/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession)
                        .content(objectMapper.writeValueAsString(testUnitGoblinArcher)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void givenUserAndUnit_whenGetUnit_thenReturnsUnitAndOK() throws Exception {

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

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        List<Unit> testUnits = new ArrayList<>();
        testUnits.add(testUnitGoblinArcher);
        testUser.setUnits(testUnits);

        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(unitService.find(1L, 1L)).thenReturn(Optional.ofNullable(testUnitGoblinArcher));

        assert testUnitGoblinArcher != null;
        mvc.perform(get("/api/users/1/units/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").isNotEmpty())
                .andExpect(jsonPath("$.type").value(testUnitGoblinArcher.getType()));
    }

    @Test
    public void givenWrongRequest_whenGetUnit_thenReturnsNotFound() throws Exception {

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

        mvc.perform(get("/api/users/1/units/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(mockSession))
                .andExpect(status().isNotFound());
    }
}
