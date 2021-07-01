package pl.adabawolska.simpleeconomygamespringboot.building.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.CreateBuildingRequest;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.GetBuildingResponse;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.GetBuildingsResponse;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.service.BuildingService;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;

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
    public ResponseEntity<GetBuildingsResponse> getBuildings(@PathVariable("id") Long id) {
        Optional<User> user = userService.find(id);
        return user.map(value -> ResponseEntity.ok(GetBuildingsResponse.entityToDtoMapper().apply(buildingService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{bid}")
    public ResponseEntity<GetBuildingResponse> getBuilding(@PathVariable("id") Long id,
                                                           @PathVariable("bid") Long buildingId) {
        return buildingService.find(id, buildingId)
                .map(value -> ResponseEntity.ok(GetBuildingResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createBuilding(@PathVariable("id") Long id,
                                               @RequestBody CreateBuildingRequest request,
                                               UriComponentsBuilder builder) {
        Optional<User> user = userService.find(id);
        if (user.isPresent()) {
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
    }
}
