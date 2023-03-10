package com.ada.simpleeconomygamespringboot.unit.controller;

import com.ada.simpleeconomygamespringboot.unit.dto.GetUnitResponse;
import com.ada.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ada.simpleeconomygamespringboot.unit.dto.GetUnitsResponse;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/users/{id}/units")
public class UserUnitController {

    private final UserService userService;

    private final UnitService unitService;

    @Autowired
    public UserUnitController(UserService userService, UnitService unitService) {
        this.userService = userService;
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<GetUnitsResponse> getUnits(@PathVariable("id") Long id) {
        Optional<User> user = userService.find(id);
        if (user.isPresent()) {
            return user.map(value -> ResponseEntity.ok(GetUnitsResponse.entityToDtoMapper().apply(unitService.findAll(value))))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{uid}")
    public ResponseEntity<GetUnitResponse> getUnit(@PathVariable("id") Long id, @PathVariable("uid") Long unitId) {
        Optional<Unit> unit = unitService.find(id, unitId);
        if (unit.isPresent()) {
            return unit.map(value -> ResponseEntity.ok(GetUnitResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{uid}")
    public ResponseEntity<Void> updateUnit(@RequestBody UpdateUnitRequest request,
                                           @PathVariable("id") Long id, @PathVariable("uid") Long unitId) {
        Optional<Unit> unit = unitService.find(id, unitId);
        if (unit.isPresent()) {
            if (unitService.canRecruit(request, id, unit.get())) {
                unit.get().setAmount(unit.get().getAmount() + request.getAmount());
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