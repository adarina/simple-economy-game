package pl.adabawolska.simpleeconomygamespringboot.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    Building findByUserIdAndType(Long id, String type);

    List<Building> findByUserId(Long id);

    Optional<Building> findByIdAndUser(Long id, User user);

    List<Building> findAllByUser(User user);
}
