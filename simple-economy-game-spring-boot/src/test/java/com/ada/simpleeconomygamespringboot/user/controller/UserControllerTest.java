package com.ada.simpleeconomygamespringboot.user.controller;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import com.ada.simpleeconomygamespringboot.user.service.UserService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private UnitService unitService;

    @MockBean
    private AuthenticationManager authorizationManager;

    @Value("${secret.key}")
    private String KEY;


    @Test
    public void givenUser_whenGetUser_thenReturnsUserAndOK() throws Exception {

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

        when(userService.find(1L)).thenReturn(Optional.of(testUser));

        mvc.perform(get("/api/users/all/{id}", 1)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.username").value("tester"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUser_whenGetUser_thenReturnsUserAndForbidden() throws Exception {

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

        when(userService.find(1L)).thenReturn(Optional.of(testUser));

        mvc.perform(get("/api/users/all/{id}", 1)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenUser_whenGetUsers_thenReturnsUserListAndOK() throws Exception {

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

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        List<User> userList = new ArrayList<>();
        userList.add(testUser);

        when(userService.findAll()).thenReturn(userList);

        mvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.users").exists())
                .andExpect(jsonPath("$.users[*].username").value("tester"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenUser_whenCreateUser_thenReturnsNotFound() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("ROLE_USER")
                .buildUserEntity();

        User testUserWithId = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUserWithId, 1L);

        when(userService.find(testUser.getUsername())).thenReturn(Optional.ofNullable(testUserWithId));

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUser_whenCreateUser_thenReturnsCreated() throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("tester");

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("ROLE_USER")
                .buildUserEntity();

        User testUserWithId = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword(hashedPassword)
                .withRole("ROLE_USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUserWithId, 1L);

        Resource resourceMud = ResourceBuilder.aResource().defaultBuildMudEntity(testUserWithId);
        Resource resourceStone = ResourceBuilder.aResource().defaultBuildStoneEntity(testUserWithId);
        Resource resourceMeat = ResourceBuilder.aResource().defaultBuildMeatEntity(testUserWithId);

        Unit unitGoblin = UnitBuilder.anUnit().defaultBuildGoblinArcherEntity(testUserWithId);
        Unit unitOrc = UnitBuilder.anUnit().defaultBuildOrcWarriorEntity(testUserWithId);
        Unit unitTroll = UnitBuilder.anUnit().defaultBuildUglyTrollEntity(testUserWithId);

        when(userService.find(testUser.getUsername())).thenReturn(Optional.empty());
        when(userService.create(any(User.class))).thenReturn(testUserWithId);
        when(resourceService.create(any(Resource.class))).thenReturn(resourceMud);
        when(resourceService.create(any(Resource.class))).thenReturn(resourceStone);
        when(resourceService.create(any(Resource.class))).thenReturn(resourceMeat);
        when(unitService.create(any(Unit.class))).thenReturn(unitGoblin);
        when(unitService.create(any(Unit.class))).thenReturn(unitOrc);
        when(unitService.create(any(Unit.class))).thenReturn(unitTroll);

        mvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserWithId)))
                .andExpect(status().isCreated());
    }
}