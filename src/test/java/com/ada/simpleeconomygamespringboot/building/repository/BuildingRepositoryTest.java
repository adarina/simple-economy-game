package com.ada.simpleeconomygamespringboot.building.repository;

import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.building.entity.BuildingBuilder;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
public class BuildingRepositoryTest {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUser_whenFindByUserId_thenReturnsBuilding() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        Building testBuildingGoblinsCavern = BuildingBuilder
                .aBuilding()
                .defaultBuildGoblinsCavernEntity(testUser);

        List<Building> testBuildings = new ArrayList<>();
        testBuildings.add(testBuildingMudGatherersCottage);
        testBuildings.add(testBuildingGoblinsCavern);
        testUser.setBuildings(testBuildings);

        Building respondBuildingMudGatherersCottage = buildingRepository.save(testBuildingMudGatherersCottage);
        Building respondBuildingStone = buildingRepository.save(testBuildingGoblinsCavern);

        List<Building> findBuildings = buildingRepository.findByUserId(testUser.getId());

        assertNotNull(findBuildings);
        assertEquals(respondBuildingMudGatherersCottage.getType(), findBuildings.get(0).getType());
        assertEquals(respondBuildingStone.getType(), findBuildings.get(1).getType());
        assertEquals(respondBuildingMudGatherersCottage.getUser(), findBuildings.get(0).getUser());
    }

    @Test
    public void givenUser_whenFindByNotExistingUserIdAndType_thenReturnsNull() {
        Building findBuilding = buildingRepository.findByUserIdAndType(2L, "COTTAGE");
        assertNull(findBuilding);
    }
}