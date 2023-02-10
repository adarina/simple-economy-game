package com.ada.simpleeconomygamespringboot.building.controller;

import com.ada.simpleeconomygamespringboot.building.dto.CreateBuildingRequest;
import com.ada.simpleeconomygamespringboot.building.dto.GetBuildingResponse;
import com.ada.simpleeconomygamespringboot.building.dto.GetBuildingsResponse;
import com.ada.simpleeconomygamespringboot.building.service.BuildingService;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.ada.simpleeconomygamespringboot.building.entity.Building;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("api/users/{id}/buildings")
public class UserBuildingController {

    private final UserService userService;

    private final BuildingService buildingService;

    @Autowired
    public UserBuildingController(UserService userService, BuildingService buildingService) {
        this.userService = userService;
        this.buildingService = buildingService;
    }

    @GetMapping
    public ResponseEntity<GetBuildingsResponse> getBuildings(@PathVariable("id") Long id, HttpSession session) {
        Optional<User> loggedInUser = Optional.ofNullable((User) session.getAttribute("user"));
        if (loggedInUser.isPresent()) {
            Optional<User> user = userService.find(id);
            return user.map(value -> ResponseEntity.ok(GetBuildingsResponse.entityToDtoMapper().apply(buildingService.findAll(value))))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("{bid}")
    public ResponseEntity<GetBuildingResponse> getBuilding(@PathVariable("id") Long id,
                                                           @PathVariable("bid") Long buildingId, HttpSession session) {
        Optional<User> loggedInUser = Optional.ofNullable((User) session.getAttribute("user"));
        if (loggedInUser.isPresent() && loggedInUser.get().getId().equals(id)) {
            return buildingService.find(id, buildingId)
                    .map(value -> ResponseEntity.ok(GetBuildingResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createBuilding(@PathVariable("id") Long id,
                                               @RequestBody CreateBuildingRequest request,
                                               UriComponentsBuilder builder,
                                               HttpSession session) {
        Optional<User> loggedInUser = Optional.ofNullable((User) session.getAttribute("user"));
        if (loggedInUser.isPresent()) {
            Optional<User> user = userService.find(id);
            if (user.isPresent() && loggedInUser.get().getId().equals(user.get().getId())) {
                if (buildingService.canBuild(request, id)) {
                    Building building = CreateBuildingRequest
                            .dtoToEntityMapper(user::get)
                            .apply(request);
                    building = buildingService.create(building);
                    return ResponseEntity.created(builder.pathSegment("api", "users", "{id}", "buildings", "{bid}")
                            .buildAndExpand(user.get().getId(), building.getId()).toUri()).build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
