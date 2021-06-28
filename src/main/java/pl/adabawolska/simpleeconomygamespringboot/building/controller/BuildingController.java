package pl.adabawolska.simpleeconomygamespringboot.building.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.GetBuildingResponse;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.UpdateBuildingRequest;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.service.BuildingService;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;

import java.util.Optional;


@RestController
@RequestMapping("api/users")
public class BuildingController {

    private final UserService userService;

    private final BuildingService buildingService;

    @Autowired
    public BuildingController(UserService userService, BuildingService buildingService) {
        this.userService = userService;
        this.buildingService = buildingService;
    }

    @GetMapping("{id}/buildings")
    public ResponseEntity<GetBuildingResponse> getBuilding(@PathVariable("id") Long id) {
        if (userService.find(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return buildingService.find(id)
                    .map(value -> ResponseEntity.ok(GetBuildingResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @PutMapping("{id}/buildings")
    public ResponseEntity<Void> updateBuilding(@RequestBody UpdateBuildingRequest request, @PathVariable("id") Long id) {
        Optional<Building> building = buildingService.find(id);
        if (building.isPresent()) {
            if (buildingService.canBuild(request, id)) {
                UpdateBuildingRequest.dtoToEntityUpdater().apply(building.get(), request);
                buildingService.update(building.get());
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
