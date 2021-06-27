package pl.adabawolska.simpleeconomygamespringboot.building.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.building.repository.BuildingRepository;
import pl.adabawolska.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.Optional;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    private final UserRepository userRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, UserRepository userRepository) {
        this.buildingRepository = buildingRepository;
        this.userRepository = userRepository;
    }

    public Optional<Building> find(Long id) {
        return buildingRepository.findById(id);
    }
}
