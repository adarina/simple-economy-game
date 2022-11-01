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

    List<Building> findByUserId(Long id);

    boolean existsBuildingByUserIdAndType(Long id, String type);

    boolean existsBuildingByUserId(Long id);

    Optional<Building> findByIdAndUser(Long id, User user);

    List<Building> findAllByUser(User user);
}
