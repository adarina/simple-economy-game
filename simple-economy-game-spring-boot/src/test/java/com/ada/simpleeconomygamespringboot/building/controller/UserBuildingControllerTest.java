package com.ada.simpleeconomygamespringboot.building.controller;


import com.ada.simpleeconomygamespringboot.building.dto.CreateBuildingRequest;
import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.building.entity.BuildingBuilder;
import com.ada.simpleeconomygamespringboot.building.service.BuildingService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @MockBean
    private AuthenticationManager authorizationManager;

    @MockBean
    private UserRepository userRepository;

    @Value("${secret.key}")
    private String KEY;

    @Test
    public void givenUserAndBuildings_whenGetBuildings_thenReturnsBuildingsAndOK() throws Exception {

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
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buildings").isNotEmpty())
                .andExpect(jsonPath("$.buildings[0].type").value(testBuildings.get(0).getType()))
                .andExpect(jsonPath("$.buildings[1].type").value(testBuildings.get(1).getType()));
    }

    @Test
    public void givenUserAndBuilding_whenPostBuilding_thenReturnsCreated() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_ADMIN")
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

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        Field privateIdField = Building.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testBuildingMudGatherersCottage, 1L);


        when(userService.find(1L)).thenReturn(Optional.of(testUser));
        when(buildingService.canBuild(any(CreateBuildingRequest.class), eq(1L))).thenReturn(true);
        when(buildingService.create(any(Building.class))).thenReturn(testBuildingMudGatherersCottage);

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(testBuildingMudGatherersCottage));
        if (jsonNode.has("user") && jsonNode.get("user").has("authorities")) {
            ((ObjectNode) jsonNode.get("user")).remove("authorities");
        }
        String newJsonString = objectMapper.writeValueAsString(jsonNode);

        mvc.perform(post("/api/users/1/buildings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(newJsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenNoExistedUserId_whenPostBuilding_thenReturnsNotFound() throws Exception {

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

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(null);

        when(userService.find(1L)).thenReturn(Optional.empty());

        mvc.perform(post("/api/users/2/buildings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(testBuildingMudGatherersCottage))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUserAndBuilding_whenGetBuilding_thenReturnsBuildingAndOK() throws Exception {

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
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").isNotEmpty())
                .andExpect(jsonPath("$.type").value(testBuildingMudGatherersCottage.getType()));
    }

    @Test
    public void givenWrongRequest_whenGetBuilding_thenReturnsNotFound() throws Exception {

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

        mvc.perform(get("/api/users/1/buildings/3")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
