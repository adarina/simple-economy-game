package com.ada.simpleeconomygamespringboot.unit.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:unit.properties")
public class UnitProperties {

    @Value("${unit.Goblin_Archer_mud}")
    private Long goblinArcherMudCost;

    @Value("${unit.Orc_Warrior_stone}")
    private Long orcWarriorStoneCost;

    @Value("${unit.Ugly_Troll_mud}")
    private Long uglyTrollMudCost;

    @Value("${unit.Ugly_Troll_stone}")
    private Long uglyTrollStoneCost;

    @Value("${unit.Goblin_Archer_meat}")
    private Long goblinArcherMeatCost;

    @Value("${unit.Orc_Warrior_meat}")
    private Long orcWarriorMeatCost;

    @Value("${unit.Ugly_Troll_meat}")
    private Long uglyTrollMeatCost;
}

