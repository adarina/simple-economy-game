package pl.adabawolska.simpleeconomygamespringboot.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    Building findByUserId(Long userId);
}
