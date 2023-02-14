package com.ada.simpleeconomygamespringboot.unit.controller;

import com.ada.simpleeconomygamespringboot.unit.dto.GetUnitsResponse;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @MockBean
    private AuthenticationManager authorizationManager;

    @MockBean
    private UserRepository userRepository;

    @Value("${secret.key}")
    private String KEY;


    @Test
    public void givenUserAndUnits_whenGetUnits_thenReturnsUnitsAndOK() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_USER")
                .buildUserEntity();

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        assert testUser != null;
        String token = JWT.create()
                .withSubject(testUser.getUsername())
                .withIssuer("Eminem")
                .withClaim("roles", testUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

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
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.units").isNotEmpty())
                .andExpect(jsonPath("$.units[0].type").value("GOBLIN"))
                .andExpect(jsonPath("$.units[1].type").value("TROLL"))
                .andExpect(jsonPath("$.units[2].type").value("ORC"));
    }


    @Test
    public void givenUserToGoblinArcherInUnit_whenUpdateUnit_thenReturnsAccepted() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_USER")
                .buildUserEntity();

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        assert testUser != null;
        String token = JWT.create()
                .withSubject(testUser.getUsername())
                .withIssuer("Eminem")
                .withClaim("roles", testUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        Field privateIdField = Unit.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUnitGoblinArcher, 1L);

        when(unitService.find(1L, 1L)).thenReturn(Optional.of(testUnitGoblinArcher));
        when(unitService.canRecruit(any(),any(), any())).thenReturn(true);
        when(unitService.update(any())).thenReturn(testUnitGoblinArcher);

        mvc.perform(put("/api/users/1/units/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUnitGoblinArcher)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void givenUserAndUnit_whenGetUnit_thenReturnsUnitAndOK() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_USER")
                .buildUserEntity();

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        assert testUser != null;
        String token = JWT.create()
                .withSubject(testUser.getUsername())
                .withIssuer("Eminem")
                .withClaim("roles", testUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

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
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").isNotEmpty())
                .andExpect(jsonPath("$.type").value(testUnitGoblinArcher.getType()));
    }

    @Test
    public void givenWrongRequest_whenGetUnit_thenReturnsNotFound() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_USER")
                .buildUserEntity();

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        assert testUser != null;
        String token = JWT.create()
                .withSubject(testUser.getUsername())
                .withIssuer("Eminem")
                .withClaim("roles", testUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        mvc.perform(get("/api/users/1/units/6")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
