package pl.adabawolska.simpleeconomygamespringboot.unit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    Unit findByUserId(Long userId);
}
