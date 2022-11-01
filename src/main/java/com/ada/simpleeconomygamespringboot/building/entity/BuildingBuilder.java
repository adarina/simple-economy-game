package com.ada.simpleeconomygamespringboot.building.entity;

import com.ada.simpleeconomygamespringboot.user.entity.User;

public class BuildingBuilder {

    private BuildingBuilder() {
    }

    public static BuildingBuilder aBuilding() {
        return new BuildingBuilder();
    }

    public Building defaultBuildMudGatherersCottageEntity(User user) {
        Building building = new Building();
        building.setType("COTTAGE");
        building.setUser(user);
        return building;
    }

    public Building defaultBuildStoneQuarryEntity(User user) {
        Building building = new Building();
        building.setType("QUARRY");
        building.setUser(user);
        return building;
    }

    public Building defaultBuildHuntersHutEntity(User user) {
        Building building = new Building();
        building.setType("HUT");
        building.setUser(user);
        return building;
    }

    public Building defaultBuildGoblinsCavernEntity(User user) {
        Building building = new Building();
        building.setType("CAVERN");
        building.setUser(user);
        return building;
    }

    public Building defaultBuildOrcsPitEntity(User user) {
        Building building = new Building();
        building.setType("PIT");
        building.setUser(user);
        return building;
    }

    public Building defaultBuildTrollsCaveEntity(User user) {
        Building building = new Building();
        building.setType("CAVE");
        building.setUser(user);
        return building;
    }
}
