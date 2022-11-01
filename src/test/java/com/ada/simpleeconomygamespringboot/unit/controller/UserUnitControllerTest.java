package com.ada.simpleeconomygamespringboot.unit.controller;

import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.units").isNotEmpty())
                .andExpect(jsonPath("$.units[0].type").value("GOBLIN"))
                .andExpect(jsonPath("$.units[1].type").value("TROLL"))
                .andExpect(jsonPath("$.units[2].type").value("ORC"));
    }

    @Test
    public void whenUserNotFound_thenReturnsNotFound() throws Exception {
        when(unitService.existsByUserId(1L)).thenReturn(false);

        mvc.perform(get("/users/1/units"))
                .andExpect(status().isNotFound());
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
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").isNotEmpty())
                .andExpect(jsonPath("$.type").value(testUnitGoblinArcher.getType()));
    }

    @Test
    public void givenWrongRequest_whenGetUnit_thenReturnsNotFound() throws Exception {
        mvc.perform(get("/api/users/1/units/6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
