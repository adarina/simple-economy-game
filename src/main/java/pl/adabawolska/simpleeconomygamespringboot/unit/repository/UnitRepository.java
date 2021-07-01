package pl.adabawolska.simpleeconomygamespringboot.unit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

     Unit findByUserIdAndType(Long id, String type);

    Optional<Unit> findByIdAndUser(Long id, User user);

    List<Unit> findAllByUser(User user);

    List<Unit> findByUserId(Long userId);
}
