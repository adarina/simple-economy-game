package com.ada.simpleeconomygamespringboot.unit.controller;

import com.ada.simpleeconomygamespringboot.unit.dto.GetUnitResponse;
import com.ada.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ada.simpleeconomygamespringboot.unit.dto.GetUnitsResponse;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.service.UserService;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<GetUnitsResponse> getUnits(@PathVariable("id") Long id, @RequestHeader("Session-Token") String sessionToken) {
        User existingUser = userService.findBySessionToken(sessionToken);
        if (existingUser.getId().equals(id)) {
            Optional<User> user = userService.find(id);
            return user.map(value -> ResponseEntity.ok(GetUnitsResponse.entityToDtoMapper().apply(unitService.findAll(value))))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("{uid}")
    public ResponseEntity<GetUnitResponse> getUnit(@PathVariable("id") Long id, @PathVariable("uid") Long unitId, @RequestHeader("Session-Token") String sessionToken) {
        User existingUser = userService.findBySessionToken(sessionToken);
        if (existingUser.getId().equals(id)) {
            return unitService.find(id, unitId)
                    .map(value -> ResponseEntity.ok(GetUnitResponse.entityToDtoMapper().apply(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("{uid}")
    public ResponseEntity<Void> updateUnit(@RequestBody UpdateUnitRequest request,
                                           @PathVariable("id") Long id, @PathVariable("uid") Long unitId, @RequestHeader("Session-Token") String sessionToken) {
        User existingUser = userService.findBySessionToken(sessionToken);
        if (existingUser.getId().equals(id)) {
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
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
