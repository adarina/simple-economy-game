package pl.adabawolska.simpleeconomygamespringboot.building.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.CreateBuildingRequest;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.properties.BuildingProperties;
import pl.adabawolska.simpleeconomygamespringboot.building.repository.BuildingRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;
import pl.adabawolska.simpleeconomygamespringboot.unit.service.UnitService;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;
import pl.adabawolska.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    private final BuildingProperties buildingProperties;

    private final UserRepository userRepository;

    private final ResourceRepository resourceRepository;

    private final ResourceService resourceService;

    private final UnitService unitService;


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
                && resourceMud.getAmount() >= buildingProperties.getStoneQuarryStoneCost()) {

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
}
