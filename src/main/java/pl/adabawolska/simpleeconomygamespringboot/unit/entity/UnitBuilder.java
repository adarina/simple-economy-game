package pl.adabawolska.simpleeconomygamespringboot.unit.entity;

import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

public class UnitBuilder {

    private UnitBuilder() {
    }

    public static UnitBuilder anUnit() {
        return new UnitBuilder();
    }

    public Unit defaultBuildGoblinArcherEntity(User user) {
        Unit unit = new Unit();
        unit.setType("GOBLIN");
        unit.setAmount(5L);
        unit.setActive(false);
        unit.setUser(user);
        return unit;
    }

    public Unit defaultBuildOrcWarriorEntity(User user) {
        Unit unit = new Unit();
        unit.setType("ORC");
        unit.setAmount(0L);
        unit.setActive(false);
        unit.setUser(user);
        return unit;
    }

    public Unit defaultBuildUglyTrollEntity(User user) {
        Unit unit = new Unit();
        unit.setType("TROLL");
        unit.setAmount(0L);
        unit.setActive(false);
        unit.setUser(user);
        return unit;
    }
}
