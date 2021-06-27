package pl.adabawolska.simpleeconomygamespringboot.building.entity;

import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

public class BuildingBuilder {

    private User user;

    private BuildingBuilder() {
    }

    public static BuildingBuilder aBuilding() {
        return new BuildingBuilder();
    }

    public Building defaultBuild() {
        Building building = new Building();
        building.setMudGatherersCottageQuantity(0L);
        building.setStoneQuarryQuantity(0L);
        building.setHuntersHutQuantity(0L);
        building.setGoblinsCavernOwnership(false);
        building.setOrcsPitOwnership(false);
        building.setTrollsCaveOwnership(false);
        building.setUser(user);
        return building;
    }
}

