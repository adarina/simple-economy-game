package com.ada.simpleeconomygamespringboot.resource.controller;

import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ada.simpleeconomygamespringboot.resource.dto.GetResourcesResponse;
import com.ada.simpleeconomygamespringboot.user.entity.User;

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
