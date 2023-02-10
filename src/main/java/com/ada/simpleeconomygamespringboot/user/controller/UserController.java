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
    public ResponseEntity<GetUsersResponse> getUsers(HttpSession session) {
        Optional<User> loggedInUser = Optional.ofNullable((User) session.getAttribute("user"));
        if (loggedInUser.isPresent() && loggedInUser.get().getRole().equals("ADMIN")) {
            return ResponseEntity.ok(GetUsersResponse.entityToDtoMapper().apply(userService.findAll()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("id") Long id) {
        return  userService.find(id)
                .map(value -> ResponseEntity.ok(GetUserResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder, HttpSession session) {
        Optional<User> loggedInUser = Optional.ofNullable((User) session.getAttribute("user"));
        if (loggedInUser.isEmpty()) {
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
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id, HttpSession session) {
        Optional<User> loggedInUser = Optional.ofNullable((User) session.getAttribute("user"));
        if (loggedInUser.isPresent() && (loggedInUser.get().getId().equals(id) || loggedInUser.get().getRole().equals("ADMIN"))) {
            Optional<User> user = userService.find(id);
            if (user.isPresent()) {
                userService.delete(user.get().getId());
                session.removeAttribute("user");
                session.invalidate();
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpSession session) {
        User existingUser = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            session.setAttribute("user", existingUser);
            return ResponseEntity.ok("User logged in successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logged")
    public ResponseEntity<String> loggedUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok("Welcome, " + user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return ResponseEntity.ok("User logged out successfully");
    }
}
