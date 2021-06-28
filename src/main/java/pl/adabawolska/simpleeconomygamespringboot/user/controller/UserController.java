package pl.adabawolska.simpleeconomygamespringboot.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.BuildingBuilder;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import pl.adabawolska.simpleeconomygamespringboot.user.dto.CreateUserRequest;
import pl.adabawolska.simpleeconomygamespringboot.user.dto.GetUserResponse;
import pl.adabawolska.simpleeconomygamespringboot.user.dto.GetUsersResponse;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;


@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    private final ResourceService resourceService;

    @Autowired
    public UserController(UserService userService, ResourceService resourceService) {
        this.userService = userService;
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(GetUsersResponse.entityToDtoMapper().apply(userService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("id") Long id) {
        return  userService.find(id)
                .map(value -> ResponseEntity.ok(GetUserResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder) {
        Resource resourceDefault = ResourceBuilder.aResource().defaultBuild();
        Building buildingDefault = BuildingBuilder.aBuilding().defaultBuild();
        Unit unitDefault = UnitBuilder.anUnit().defaultBuild();
        User user = CreateUserRequest
                .dtoToEntityMapper(resourceDefault, buildingDefault, unitDefault)
                .apply(request);
        if (userService.find(user.getId()).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            user = userService.create(user);
            return ResponseEntity.created(builder.pathSegment("api", "users", "{id}")
                    .buildAndExpand(user.getId()).toUri()).build();
        }
    }
}
