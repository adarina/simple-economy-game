package pl.adabawolska.simpleeconomygamespringboot.unit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adabawolska.simpleeconomygamespringboot.building.repository.BuildingRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import pl.adabawolska.simpleeconomygamespringboot.resource.service.ResourceService;
import pl.adabawolska.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;
import pl.adabawolska.simpleeconomygamespringboot.unit.properties.UnitProperties;
import pl.adabawolska.simpleeconomygamespringboot.unit.repository.UnitRepository;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;
import pl.adabawolska.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    private final BuildingRepository buildingRepository;

    private final ResourceRepository resourceRepository;

    private final UnitProperties unitProperties;

    private final ResourceService resourceService;

    private final UserRepository userRepository;


    @Autowired
    public UnitService(UnitRepository unitRepository, BuildingRepository buildingRepository,
                       ResourceRepository resourceRepository, UnitProperties unitProperties,
                       ResourceService resourceService, UserRepository userRepository) {
        this.unitRepository = unitRepository;
        this.buildingRepository = buildingRepository;
        this.resourceRepository = resourceRepository;
        this.unitProperties = unitProperties;
        this.resourceService = resourceService;

        this.userRepository = userRepository;
    }

    @Transactional
    public Unit create(Unit unit) {
        return unitRepository.save(unit);
    }

    public List<Unit> findAll(User user) {
        return unitRepository.findAllByUser(user);
    }

    public Optional<Unit> find(Long id, Long unitId) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return unitRepository.findByIdAndUser(unitId, user.get());
        } else {
            return Optional.empty();
        }
    }

    public void changeGoblinArcherActivity(Long id, Boolean activity) {
        Unit unit = unitRepository.findByUserIdAndType(id, "GOBLIN");
        unit.setActive(activity);
        unitRepository.save(unit);
    }

    public void changeOrcWarriorActivity(Long id, Boolean activity) {
        Unit unit = unitRepository.findByUserIdAndType(id, "ORC");
        unit.setActive(activity);
        unitRepository.save(unit);
    }

    public void changeUglyTrollActivity(Long id, Boolean activity) {
        Unit unit = unitRepository.findByUserIdAndType(id, "TROLL");
        unit.setActive(activity);
        unitRepository.save(unit);
    }

    public void changeAmount(Unit unit, Long amountToAdd) {
        if (unit.getAmount() + amountToAdd < 0) {
            unit.setAmount(0L);
        } else {
            System.out.println(unit.getAmount() + amountToAdd);
            unit.setAmount(unit.getAmount() + amountToAdd);
        }
        unitRepository.save(unit);
    }

    public Optional<Unit> find(Long id) {
        return unitRepository.findById(id);
    }

    public List<Unit> findUnitByUserId(Long id) {
        return unitRepository.findByUserId(id);
    }

    public boolean canRecruit(UpdateUnitRequest unit, Long id) {

        Resource resourceMud = resourceRepository.findByUserIdAndType(id,"MUD");
        Resource resourceStone = resourceRepository.findByUserIdAndType(id,"STONE");

        Unit unitGoblin = unitRepository.findByUserIdAndType(id, "GOBLIN");
        Unit unitOrc = unitRepository.findByUserIdAndType(id, "ORC");
        Unit unitTroll = unitRepository.findByUserIdAndType(id, "TROLL");

        if (unit.getType().equals("GOBLIN") && unitGoblin.getActive()
                && resourceMud.getAmount() >= unitProperties.getGoblinArcherMudCost()) {
            resourceService.changeMudQuantity(id, (-unitProperties.getGoblinArcherMudCost()) * unit.getAmount());
            changeAmount(unitGoblin, unit.getAmount());
            return true;
        } else if (unit.getType().equals("ORC") && unitOrc.getActive()
                && resourceStone.getAmount() >= unitProperties.getOrcWarriorStoneCost()) {
            resourceService.changeStoneQuantity(id, (-unitProperties.getOrcWarriorStoneCost()) * unit.getAmount());
            changeAmount(unitOrc, unit.getAmount());
            return true;
        } else if (unit.getType().equals("TROLL") && unitTroll.getActive()
                && resourceMud.getAmount() >= unitProperties.getUglyTrollMudCost()
                && resourceStone.getAmount() >= unitProperties.getUglyTrollStoneCost()) {
            resourceService.changeMudQuantity(id, (-unitProperties.getUglyTrollMudCost()) * unit.getAmount());
            resourceService.changeStoneQuantity(id, (-unitProperties.getUglyTrollStoneCost()) * unit.getAmount());
            changeAmount(unitOrc, unit.getAmount());
            return true;
        }
        return false;
    }

    @Transactional
    public void update(Unit unit) {
        unitRepository.save(unit);
    }
/*
    public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }*/
}
