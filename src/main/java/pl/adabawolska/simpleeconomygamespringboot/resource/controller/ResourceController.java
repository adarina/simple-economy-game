package pl.adabawolska.simpleeconomygamespringboot.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adabawolska.simpleeconomygamespringboot.resource.dto.GetResourceResponse;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;


@RestController
@RequestMapping("api/users")
public class ResourceController {

    private final UserService userService;

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(UserService userService, ResourceService resourceService) {
        this.userService = userService;
        this.resourceService = resourceService;
    }

    @GetMapping("{id}/resources")
    public ResponseEntity<GetResourceResponse> getResource(@PathVariable("id") Long id) {
        if (userService.find(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return resourceService.find(id)
                    .map(value -> ResponseEntity.ok(GetResourceResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }
}
