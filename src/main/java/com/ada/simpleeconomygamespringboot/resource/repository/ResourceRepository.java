package com.ada.simpleeconomygamespringboot.resource.repository;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ada.simpleeconomygamespringboot.user.entity.User;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findByUserId(Long userId);

    Resource findByUserIdAndType(Long userId, String type);

    List<Resource> findAllByUser(User user);

    boolean existsResourceByUserId(Long id);

}
