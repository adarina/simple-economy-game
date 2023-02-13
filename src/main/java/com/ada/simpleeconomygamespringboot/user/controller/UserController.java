package com.ada.simpleeconomygamespringboot.user.controller;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.dto.*;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.user.entity.User;


import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    private final ResourceService resourceService;

    private final UnitService unitService;

    private final AuthenticationManager authorizationManager;


    @Value("${secret.key}")
    private String KEY;

    @Autowired
    public UserController(UserService userService, ResourceService resourceService, UnitService unitService, AuthenticationManager authorizationManager) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.unitService = unitService;
        this.authorizationManager = authorizationManager;
    }

    @GetMapping("/all")
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(GetUsersResponse.entityToDtoMapper().apply(userService.findAll()));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("id") Long id) {
        return userService.find(id)
                .map(value -> ResponseEntity.ok(GetUserResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder) {

        User user = CreateUserRequest
                .dtoToEntityMapper()
                .apply(request);
        if (userService.find(user.getUsername()).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(hashedPassword);
            user = userService.create(user);

            Resource resourceMud = ResourceBuilder.aResource().defaultBuildMudEntity(user);
            Resource resourceStone = ResourceBuilder.aResource().defaultBuildStoneEntity(user);
            Resource resourceMeat = ResourceBuilder.aResource().defaultBuildMeatEntity(user);

            resourceService.create(resourceMud);
            resourceService.create(resourceStone);
            resourceService.create(resourceMeat);

            Unit unitGoblin = UnitBuilder.anUnit().defaultBuildGoblinArcherEntity(user);
            Unit unitOrc = UnitBuilder.anUnit().defaultBuildOrcWarriorEntity(user);
            Unit unitTroll = UnitBuilder.anUnit().defaultBuildUglyTrollEntity(user);

            unitService.create(unitGoblin);
            unitService.create(unitOrc);
            unitService.create(unitTroll);

            return ResponseEntity.created(builder.pathSegment("api", "users", "{username}")
                    .buildAndExpand(user.getUsername()).toUri()).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.find(id);
        if (user.isPresent()) {
            userService.delete(user.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody CreateLoginRequest createLoginRequest) {
        try {
            Authentication authenticate = authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(createLoginRequest.getUsername(), createLoginRequest.getPassword()));
            User user = (User) authenticate.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer("Eminem")
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            GetLoginResponse getLoginResponse = new GetLoginResponse(user.getUsername(), token);
            getLoginResponse.setId(user.getId());
            getLoginResponse.setRole(user.getRole());
            return ResponseEntity.ok(getLoginResponse);
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}