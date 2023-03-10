package com.ada.simpleeconomygamespringboot.configuration;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.building.properties.BuildingProperties;
import com.ada.simpleeconomygamespringboot.building.repository.BuildingRepository;
import com.ada.simpleeconomygamespringboot.unit.properties.UnitProperties;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private final ResourceService resourceService;

    private final UnitService unitService;

    private final UnitProperties unitProperties;

    private final BuildingProperties buildingProperties;

    private final ResourceRepository resourceRepository;

    private final UserRepository userRepository;

    private final BuildingRepository buildingRepository;

    private final UnitRepository unitRepository;

    @Autowired
    public ScheduleConfig(UnitService unitService, ResourceService resourceService, UnitProperties unitProperties,
                          BuildingProperties buildingProperties, ResourceRepository resourceRepository,
                          UserRepository userRepository, BuildingRepository buildingRepository,
                          UnitRepository unitRepository) {

        this.unitService = unitService;
        this.resourceService = resourceService;
        this.unitProperties = unitProperties;
        this.buildingProperties = buildingProperties;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
        this.buildingRepository = buildingRepository;
        this.unitRepository = unitRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {

        List<User> users = userRepository.findAll();
        for (User user: users) {
            Long userId = user.getId();

            Resource resourceMud = resourceRepository.findByUserIdAndType(userId,"MUD");
            Resource resourceStone = resourceRepository.findByUserIdAndType(userId,"STONE");
            Resource resourceMeat = resourceRepository.findByUserIdAndType(userId,"MEAT");

            Long mud = resourceMud.getAmount();
            Long stone = resourceStone.getAmount();
            Long meat = resourceMeat.getAmount();

            Long newMud = mud;
            Long newStone = stone;
            Long newMeat = meat;

            List<Building> buildings = buildingRepository.findByUserId(userId);
            for (Building building: buildings) {
                switch (building.getType()) {
                    case "COTTAGE":
                        newMud += buildingProperties.getMudGatherersCottageProd();
                        break;
                    case "QUARRY":
                        newStone += buildingProperties.getStoneQuarryProd();
                        break;
                    case "HUT":
                        newMeat += buildingProperties.getHuntersHutProd();
                        break;
                }
            }

            resourceMud.setAmount(newMud);
            resourceStone.setAmount(newStone);
            resourceMeat.setAmount(newMeat);

            resourceService.create(resourceMud);
            resourceService.create(resourceStone);
            resourceService.create(resourceMeat);

            meat = resourceMeat.getAmount();

            List<Unit> units = unitRepository.findByUserId(userId);
            for (Unit unit: units) {
                if (unit.getType().equals("GOBLIN") && unit.getActive()) {
                    resourceMeat.setAmount(meat - (unitProperties.getGoblinArcherMeatCost() * unit.getAmount()));
                } else if (unit.getType().equals("ORC") && unit.getActive()) {
                    resourceMeat.setAmount(meat - (unitProperties.getOrcWarriorMeatCost() * unit.getAmount()));
                } else if (unit.getType().equals("TROLL") && unit.getActive()) {
                    resourceMeat.setAmount(meat - (unitProperties.getUglyTrollMeatCost() * unit.getAmount()));
                }
            }
            if (resourceMeat.getAmount() < 0) {
                Long newMeatSum = desertion(units, resourceMeat.getAmount());
                resourceMeat.setAmount(newMeatSum);
            }
            resourceService.create(resourceMeat);
        }
    }

    private long desertion(List<Unit> units, Long meatSum) {
        while (meatSum < 0) {
            for (Unit unit : units) {
                if (unit.getType().equals("GOBLIN") && unit.getActive() && unit.getAmount() > 0) {
                    unit.setAmount(unit.getAmount() - 1);
                    meatSum += unitProperties.getGoblinArcherMeatCost();
                } else if (unit.getType().equals("ORC") && unit.getActive() && unit.getAmount() > 0) {
                    unit.setAmount(unit.getAmount() - 1);
                    meatSum += unitProperties.getOrcWarriorMeatCost();
                } else if (unit.getType().equals("TROLL") && unit.getActive() && unit.getAmount() > 0) {
                    unit.setAmount(unit.getAmount() - 1);
                    meatSum += unitProperties.getUglyTrollMeatCost();
                }
                unitService.update(unit);
            }
        }
        return meatSum;
    }
}
