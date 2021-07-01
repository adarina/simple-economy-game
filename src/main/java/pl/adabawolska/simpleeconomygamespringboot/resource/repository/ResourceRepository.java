package pl.adabawolska.simpleeconomygamespringboot.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByUserId(Long userId);

    Resource findByUserIdAndType(Long userId, String type);

    List<Resource> findAllByUser(User user);
}
