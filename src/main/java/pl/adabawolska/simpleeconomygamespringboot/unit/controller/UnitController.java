package pl.adabawolska.simpleeconomygamespringboot.unit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adabawolska.simpleeconomygamespringboot.unit.dto.GetUnitResponse;
import pl.adabawolska.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;
import pl.adabawolska.simpleeconomygamespringboot.unit.service.UnitService;
import pl.adabawolska.simpleeconomygamespringboot.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UnitController {

    private final UserService userService;

    private final UnitService unitService;

    @Autowired
    public UnitController(UserService userService, UnitService unitService) {
        this.userService = userService;
        this.unitService = unitService;
    }

    @GetMapping("{id}/units")
    public ResponseEntity<GetUnitResponse> getUnit(@PathVariable("id") Long id) {
        if (userService.find(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return unitService.find(id)
                    .map(value -> ResponseEntity.ok(GetUnitResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @PutMapping("{id}/units")
    public ResponseEntity<Void> updateUnit(@RequestBody UpdateUnitRequest request, @PathVariable("id") Long id) {
        Optional<Unit> unit = unitService.find(id);
        if (unit.isPresent()) {
            if (unitService.canRecruit(request, id)) {
                UpdateUnitRequest.dtoToEntityUpdater().apply(unit.get(), request);
                unitService.update(unit.get());
                return ResponseEntity.accepted().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}