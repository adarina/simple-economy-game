package pl.adabawolska.simpleeconomygamespringboot.unit.entity;

import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

public final class UnitBuilder {

    private User user;

    private UnitBuilder() {
    }

    public static UnitBuilder anUnit() {
        return new UnitBuilder();
    }

    public Unit defaultBuild() {
        Unit unit = new Unit();
        unit.setGoblinArcherQuantity(0L);
        unit.setOrcWarriorQuantity(0L);
        unit.setUglyTrollQuantity(0L);
        unit.setUser(user);
        return unit;
    }
}
