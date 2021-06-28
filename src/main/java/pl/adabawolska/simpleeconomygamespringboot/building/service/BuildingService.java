package pl.adabawolska.simpleeconomygamespringboot.building.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adabawolska.simpleeconomygamespringboot.building.dto.UpdateBuildingRequest;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.properties.BuildingProperties;
import pl.adabawolska.simpleeconomygamespringboot.building.repository.BuildingRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;

import java.util.Optional;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    private final ResourceRepository resourceRepository;

    private final BuildingProperties buildingProperties;

    private final ResourceService resourceService;


    @Autowired
    public BuildingService(BuildingRepository buildingRepository, ResourceRepository resourceRepository,
                           BuildingProperties buildingProperties, ResourceService resourceService) {
        this.buildingRepository = buildingRepository;
        this.resourceRepository = resourceRepository;
        this.buildingProperties = buildingProperties;
        this.resourceService = resourceService;

    }

    public Optional<Building> find(Long id) {
        return buildingRepository.findById(id);
    }

    public boolean canBuild(UpdateBuildingRequest building, Long id) {

        Building buildingPrevious = buildingRepository.findByUserId(id);
        Resource resourcePrevious = resourceRepository.findByUserId(id);

        if (!buildingPrevious.getMudGatherersCottageQuantity().equals(building.getMudGatherersCottage())
                && resourcePrevious.getMudQuantity() >= buildingProperties.getMudGatherersCottageMudCost()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getMudGatherersCottageMudCost());
            return true;

        } else if (!buildingPrevious.getStoneQuarryQuantity().equals(building.getStoneQuarry())
                && resourcePrevious.getMudQuantity() >= buildingProperties.getStoneQuarryMudCost()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getStoneQuarryMudCost());
            return true;
        } else if (!buildingPrevious.getHuntersHutQuantity().equals(building.getHuntersHut())
                && resourcePrevious.getMudQuantity() >= buildingProperties.getHuntersHutMudCost()
                && resourcePrevious.getStoneQuantity() >= buildingProperties.getHuntersHutStoneCost()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getHuntersHutMudCost());
            resourceService.changeStoneQuantity(id, -buildingProperties.getHuntersHutStoneCost());
            return true;
        } else if (!buildingPrevious.isGoblinsCavernOwnership() == building.isGoblinsCavern()
                && resourcePrevious.getMudQuantity() >= buildingProperties.getGoblinsCavernMudCost()
                && resourcePrevious.getStoneQuantity() >= buildingProperties.getGoblinsCavernStoneCost()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getGoblinsCavernMudCost());
            resourceService.changeStoneQuantity(id, -buildingProperties.getGoblinsCavernStoneCost());
            return true;
        } else if (!buildingPrevious.isOrcsPitOwnership() == building.isOrcsPit()
                && resourcePrevious.getMudQuantity() >= buildingProperties.getOrcsPitMudCost()
                && resourcePrevious.getStoneQuantity() >= buildingProperties.getOrcsPitStoneCost()
                && buildingPrevious.isGoblinsCavernOwnership()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getOrcsPitMudCost());
            resourceService.changeStoneQuantity(id, -buildingProperties.getOrcsPitStoneCost());
            return true;
        } else if (!buildingPrevious.isTrollsCaveOwnership() == building.isTrollsCave()
                && resourcePrevious.getMudQuantity() >= buildingProperties.getTrollsCaveMudCost()
                && resourcePrevious.getStoneQuantity() >= buildingProperties.getTrollsCaveStoneCost()
                && buildingPrevious.isOrcsPitOwnership()) {

            resourceService.changeMudQuantity(id, -buildingProperties.getTrollsCaveMudCost());
            resourceService.changeStoneQuantity(id, -buildingProperties.getTrollsCaveStoneCost());
            return true;
        }
        return false;
    }

    @Transactional
    public void update(Building building) {
        buildingRepository.save(building);
    }
}
