package com.ada.simpleeconomygamespringboot.building.repository;

import com.ada.simpleeconomygamespringboot.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ada.simpleeconomygamespringboot.building.entity.Building;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    Building findByUserIdAndType(Long id, String type);
    Optional<Building> findByIdAndUser(Long id, User user);
    List<Building> findByUserId(Long id);
    List<Building> findAllByUser(User user);

}
