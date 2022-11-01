package com.ada.simpleeconomygamespringboot.building.service;

import com.ada.simpleeconomygamespringboot.building.dto.CreateBuildingRequest;
import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.building.properties.BuildingProperties;
import com.ada.simpleeconomygamespringboot.building.repository.BuildingRepository;
import com.ada.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    private  BuildingRepository buildingRepository;

    private  BuildingProperties buildingProperties;

    private  UserRepository userRepository;

    private  ResourceRepository resourceRepository;

    private  ResourceService resourceService;

    private  UnitService unitService;

    public BuildingService() {

    }
    @Autowired
    public BuildingService(BuildingRepository buildingRepository, BuildingProperties buildingProperties,
                           UserRepository userRepository, ResourceRepository resourceRepository,
                           ResourceService resourceService, UnitService unitService) {
        this.buildingRepository = buildingRepository;
        this.buildingProperties = buildingProperties;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
        this.unitService = unitService;
    }

    public boolean canBuild(CreateBuildingRequest building, Long id) {

        Resource resourceMud = resourceRepository.findByUserIdAndType(id,"MUD");
        Resource resourceStone = resourceRepository.findByUserIdAndType(id,"STONE");

        if (building.getType().equals("COTTAGE")
                && resourceMud.getAmount() >= (buildingProperties.getMudGatherersCottageMudCost())) {

            resourceService.changeMudQuantity(id, -buildingProperties.getMudGatherersCottageMudCost());
            return true;
        } else if (building.getType().equals("QUARRY")
                && resourceMud.getAmount() >= buildingProperties.getStoneQuarryMudCost()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getStoneQuarryMudCost());
            return true;
        } else if (building.getType().equals("HUT")
                && resourceMud.getAmount() >= buildingProperties.getHuntersHutMudCost()
                && resourceStone.getAmount() >= buildingProperties.getHuntersHutStoneCost()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getHuntersHutMudCost());
            resourceService.changeStoneQuantity(id, -buildingProperties.getHuntersHutStoneCost());
            return true;
        } else if (building.getType().equals("CAVERN")
                && resourceMud.getAmount() >= buildingProperties.getGoblinsCavernMudCost()
                && resourceStone.getAmount() >= buildingProperties.getGoblinsCavernStoneCost()) {

            Building buildingOwnership = buildingRepository.findByUserIdAndType(id, "CAVERN");
            if (buildingOwnership != null) {
                return false;
            } else {
                resourceService.changeMudQuantity(id, -buildingProperties.getGoblinsCavernMudCost());
                resourceService.changeStoneQuantity(id, -buildingProperties.getGoblinsCavernStoneCost());
                unitService.changeGoblinArcherActivity(id,true);
                return true;
            }
        } else if (building.getType().equals("PIT")
                && resourceMud.getAmount() >= buildingProperties.getOrcsPitMudCost()
                && resourceStone.getAmount() >= buildingProperties.getOrcsPitStoneCost()) {

            Building buildingCavernOwnership = buildingRepository.findByUserIdAndType(id, "CAVERN");
            Building buildingPitOwnership = buildingRepository.findByUserIdAndType(id, "PIT");

            if(buildingPitOwnership != null) {
                return false;
            } else if (buildingCavernOwnership == null) {
                return false;
            } else {
                resourceService.changeMudQuantity(id, -buildingProperties.getOrcsPitMudCost());
                resourceService.changeStoneQuantity(id, -buildingProperties.getOrcsPitStoneCost());
                unitService.changeOrcWarriorActivity(id,true);
                return true;
            }
        } else if (building.getType().equals("CAVE")
                && resourceMud.getAmount() >= buildingProperties.getTrollsCaveMudCost()
                && resourceStone.getAmount() >= buildingProperties.getTrollsCaveStoneCost()) {

            Building buildingPitOwnership = buildingRepository.findByUserIdAndType(id, "PIT");
            Building buildingCaveOwnership = buildingRepository.findByUserIdAndType(id, "CAVE");

            if(buildingCaveOwnership != null) {
                return false;
            } else if (buildingPitOwnership == null) {
                return false;
            } else {
                resourceService.changeMudQuantity(id, -buildingProperties.getTrollsCaveMudCost());
                resourceService.changeStoneQuantity(id, -buildingProperties.getTrollsCaveStoneCost());
                unitService.changeUglyTrollActivity(id,true);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Building create(Building building) {
        return buildingRepository.save(building);
    }

    public List<Building> findAll(User user) {
        return buildingRepository.findAllByUser(user);
    }

    public Optional<Building> find(Long id, Long buildingId) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return buildingRepository.findByIdAndUser(buildingId, user.get());
        } else {
            return Optional.empty();
        }
    }

    public List<Building> findAllBuildings() {
        return buildingRepository.findAll();
    }

    public boolean existsByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent();
    }
}
