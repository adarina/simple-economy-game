package pl.adabawolska.simpleeconomygamespringboot.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adabawolska.simpleeconomygamespringboot.resource.dto.GetResourcesResponse;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/users/{id}/resources")
public class UserResourceController {

    private final UserService userService;

    private final ResourceService resourceService;

    @Autowired
    public UserResourceController(UserService userService, ResourceService resourceService) {
        this.userService = userService;
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<GetResourcesResponse> getResources(@PathVariable("id") Long id) {
        Optional<User> user = userService.find(id);
        return user.map(value -> ResponseEntity.ok(GetResourcesResponse.entityToDtoMapper().apply(resourceService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
