package com.ada.simpleeconomygamespringboot.user.controller;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.user.dto.CreateUserRequest;
import com.ada.simpleeconomygamespringboot.user.dto.GetUserResponse;
import com.ada.simpleeconomygamespringboot.user.dto.GetUsersResponse;
import com.ada.simpleeconomygamespringboot.user.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    private final ResourceService resourceService;

    private final UnitService unitService;

    @Autowired
    public UserController(UserService userService, ResourceService resourceService, UnitService unitService) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers(@RequestHeader("Session-Token") String sessionToken) {
        User existingUser = userService.findBySessionToken(sessionToken);
        if (existingUser != null &&  existingUser.getRole().equals("ADMIN")) {
        return ResponseEntity.ok(GetUsersResponse.entityToDtoMapper().apply(userService.findAll()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("id") Long id) {
        return userService.find(id)
                .map(value -> ResponseEntity.ok(GetUserResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder) {

        User user = CreateUserRequest
                .dtoToEntityMapper()
                .apply(request);
        if (userService.find(user.getUsername()).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id, @RequestHeader("Session-Token") String sessionToken) {
        User existingUser = userService.findBySessionToken(sessionToken);
        if (existingUser != null && (existingUser.getId().equals(id) || existingUser.getRole().equals("ADMIN"))) {
            Optional<User> user = userService.find(id);
            if (user.isPresent()) {
                userService.delete(user.get().getId());
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user, HttpSession session) {
        User existingUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            existingUser.setSessionToken(session.getId());
            userService.save(existingUser);
            Map<String, String> response = new HashMap<>();
            response.put("token", session.getId());
            response.put("role", existingUser.getRole());
            response.put("id", existingUser.getId().toString());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("Session-Token") String sessionToken) {
        User user = userService.findBySessionToken(sessionToken);
        if (user != null) {
            user.setSessionToken(null);
            userService.save(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Void> checkSession(@RequestHeader("Session-Token") String sessionToken) {
        User user = userService.findBySessionToken(sessionToken);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}