package pl.adabawolska.simpleeconomygamespringboot.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.adabawolska.simpleeconomygamespringboot.user.dto.CreateUserRequest;
import pl.adabawolska.simpleeconomygamespringboot.user.dto.GetUserResponse;
import pl.adabawolska.simpleeconomygamespringboot.user.dto.GetUsersResponse;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(GetUsersResponse.entityToDtoMapper().apply(userService.findAll()));
    }

    @GetMapping("{username}")
    public ResponseEntity<GetUserResponse> getWorker(@PathVariable("username") String username) {
        return  userService.find(username)
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
            return ResponseEntity.created(builder.pathSegment("api", "users", "{username}")
                    .buildAndExpand(user.getUsername()).toUri()).build();
        }
    }
}
