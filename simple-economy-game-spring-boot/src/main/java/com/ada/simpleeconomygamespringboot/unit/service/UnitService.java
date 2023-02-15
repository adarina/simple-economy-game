package com.ada.simpleeconomygamespringboot.unit.service;

import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ada.simpleeconomygamespringboot.building.repository.BuildingRepository;
import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest;
import com.ada.simpleeconomygamespringboot.unit.properties.UnitProperties;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    private final ResourceRepository resourceRepository;

    private final UnitProperties unitProperties;

    private final ResourceService resourceService;

    private final UserRepository userRepository;


    @Autowired
    public UnitService(UnitRepository unitRepository, ResourceRepository resourceRepository,
                       UnitProperties unitProperties, ResourceService resourceService,
                       UserRepository userRepository) {
        this.unitRepository = unitRepository;
        this.resourceRepository = resourceRepository;
        this.unitProperties = unitProperties;
        this.resourceService = resourceService;
        this.userRepository = userRepository;
    }

    public boolean canRecruit(UpdateUnitRequest unit, Long id, Unit findUnit) {

        Resource resourceMud = resourceRepository.findByUserIdAndType(id,"MUD");
        Resource resourceStone = resourceRepository.findByUserIdAndType(id,"STONE");

        if (unit.getAmount() + findUnit.getAmount() >= 0) {
            if (unit.getType().equals("GOBLIN") && findUnit.getActive()
                    && resourceMud.getAmount() >= unitProperties.getGoblinArcherMudCost()) {
                resourceService.changeMudQuantity(id, (-unitProperties.getGoblinArcherMudCost()) * unit.getAmount());

                return true;
            } else if (unit.getType().equals("ORC") && findUnit.getActive()
                    && resourceStone.getAmount() >= unitProperties.getOrcWarriorStoneCost()) {
                resourceService.changeStoneQuantity(id, (-unitProperties.getOrcWarriorStoneCost()) * unit.getAmount());

                return true;
            } else if (unit.getType().equals("TROLL") && findUnit.getActive()
                    && resourceMud.getAmount() >= unitProperties.getUglyTrollMudCost()
                    && resourceStone.getAmount() >= unitProperties.getUglyTrollStoneCost()) {
                resourceService.changeMudQuantity(id, (-unitProperties.getUglyTrollMudCost()) * unit.getAmount());
                resourceService.changeStoneQuantity(id, (-unitProperties.getUglyTrollStoneCost()) * unit.getAmount());

                return true;
            }
        }
        return false;
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

    @Transactional
    public Unit create(Unit unit) {
        return unitRepository.save(unit);
    }

    public Optional<Unit> find(Long id, Long unitId) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return unitRepository.findByIdAndUser(unitId, user.get());
        } else {
            return Optional.empty();
        }
    }

    public List<Unit> findAll(User user) {
        return unitRepository.findAllByUser(user);
    }

    public Optional<Unit> find(Long id) {
        return unitRepository.findById(id);
    }

    @Transactional
    public Unit update(Unit unit) {
        return unitRepository.save(unit);
    }

}
