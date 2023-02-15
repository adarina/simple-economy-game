package com.ada.simpleeconomygamespringboot.user.entity;


import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;

import java.util.List;

public class UserBuilder {

    private String username;
    private String password;
    private String role;
    private List<Resource> resources;
    private List<Unit> units;
    private List<Building> buildings;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public User buildUserEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setResources(resources);
        user.setUnits(units);
        user.setBuildings(buildings);

        return user;
    }
}
