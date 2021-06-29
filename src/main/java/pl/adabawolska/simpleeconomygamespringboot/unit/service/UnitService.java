package pl.adabawolska.simpleeconomygamespringboot.unit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.repository.BuildingRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;
import pl.adabawolska.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;
import pl.adabawolska.simpleeconomygamespringboot.unit.properties.UnitProperties;
import pl.adabawolska.simpleeconomygamespringboot.unit.repository.UnitRepository;

import java.util.Optional;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    private final BuildingRepository buildingRepository;

    private final ResourceRepository resourceRepository;

    private final UnitProperties unitProperties;

    private final ResourceService resourceService;


    @Autowired
    public UnitService(UnitRepository unitRepository, BuildingRepository buildingRepository,
                       ResourceRepository resourceRepository, UnitProperties unitProperties,
                       ResourceService resourceService) {
        this.unitRepository = unitRepository;
        this.buildingRepository = buildingRepository;
        this.resourceRepository = resourceRepository;
        this.unitProperties = unitProperties;
        this.resourceService = resourceService;

    }

    public Optional<Unit> find(Long id) {
        return unitRepository.findById(id);
    }

    public Unit findUnitByUserId(Long id) {
        return unitRepository.findByUserId(id);
    }

    public boolean canRecruit(UpdateUnitRequest unit, Long id) {

        Unit unitPrevious = unitRepository.findByUserId(id);
        Building buildingPrevious = buildingRepository.findByUserId(id);
        Resource resourcePrevious = resourceRepository.findByUserId(id);

        if (!unitPrevious.getGoblinArcherQuantity().equals(unit.getGoblinArcherQuantity())
                && resourcePrevious.getMudQuantity() >= unitProperties.getGoblinArcherMudCost()
                && buildingPrevious.isGoblinsCavernOwnership()) {

            resourceService.changeMudQuantity(id, -unitProperties.getGoblinArcherMudCost());
            return true;

        } else if (!unitPrevious.getOrcWarriorQuantity().equals(unit.getOrcWarriorQuantity())
                && resourcePrevious.getStoneQuantity() >= unitProperties.getOrcWarriorStoneCost()
                && buildingPrevious.isOrcsPitOwnership()) {

            resourceService.changeStoneQuantity(id, -unitProperties.getOrcWarriorStoneCost());
            return true;
        } else if (!unitPrevious.getUglyTrollQuantity().equals(unit.getUglyTrollQuantity())
                && resourcePrevious.getMudQuantity() >= unitProperties.getUglyTrollMudCost()
                && resourcePrevious.getStoneQuantity() >= unitProperties.getUglyTrollStoneCost()
                && buildingPrevious.isTrollsCaveOwnership()) {

            resourceService.changeMudQuantity(id, -unitProperties.getUglyTrollMudCost());
            resourceService.changeStoneQuantity(id, -unitProperties.getUglyTrollStoneCost());
            return true;
        }
        return false;
    }

    @Transactional
    public void update(Unit unit) {
        unitRepository.save(unit);
    }

    public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }
}
