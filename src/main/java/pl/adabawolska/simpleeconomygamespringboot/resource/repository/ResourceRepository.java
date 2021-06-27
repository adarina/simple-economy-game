package pl.adabawolska.simpleeconomygamespringboot.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
