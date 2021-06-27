package pl.adabawolska.simpleeconomygamespringboot.building.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.GetBuildingResponse;
import pl.adabawolska.simpleeconomygamespringboot.building.service.BuildingService;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;

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
}
