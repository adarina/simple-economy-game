package com.ada.simpleeconomygamespringboot.building.service;

import com.ada.simpleeconomygamespringboot.building.entity.Building;
import com.ada.simpleeconomygamespringboot.building.entity.BuildingBuilder;
import com.ada.simpleeconomygamespringboot.building.repository.BuildingRepository;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private BuildingService buildingService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void givenExistedUserId_whenCreateBuilding_thenReturnsThat() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        when(buildingRepository.save(any(Building.class))).thenReturn(testBuildingMudGatherersCottage);
        Building createdBuilding = buildingService.create(testBuildingMudGatherersCottage);

        assertThat(createdBuilding.getType()).isSameAs(testBuildingMudGatherersCottage.getType());
    }

    @Test
    public void givenNotExistedUser_whenCreate_thenReturnsThat() {

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(null);

        when(buildingRepository.save(any(Building.class))).thenReturn(null);
        Building createdBuilding = buildingService.create(testBuildingMudGatherersCottage);
        assertNull(createdBuilding);
    }

    @Test
    public void givenNotExistedUser_whenFindAll_thenReturnsNull() {
        when(buildingRepository.findAllByUser(null)).thenReturn(null);
        List<Building> responseBuilding = buildingService.findAll(null);
        assertNull(responseBuilding);
    }

    @Test
    public void givenExistedUser_whenFindAll_thenReturnsNotNull() throws NoSuchFieldException, IllegalAccessException {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        List<Building> testBuildings = new ArrayList<>();
        testBuildings.add(testBuildingMudGatherersCottage);
        testUser.setBuildings(testBuildings);

        when(buildingRepository.findAllByUser(testUser)).thenReturn(testBuildings);
        List<Building> responseBuilding = buildingService.findAll(testUser);
        assertNotNull(responseBuilding);
    }

    @Test
    public void givenNotExistedUserId_whenFind_thenReturnsFalse() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Building> responseBuilding = buildingService.find(1L, 1L);
        assertFalse(responseBuilding.isPresent());
    }

    @Test
    public void givenExistedUserId_whenFind_thenReturnsTrue() throws NoSuchFieldException, IllegalAccessException {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        Building testBuildingMudGatherersCottage = BuildingBuilder
                .aBuilding()
                .defaultBuildMudGatherersCottageEntity(testUser);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(buildingRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.ofNullable(testBuildingMudGatherersCottage));
        Optional<Building> responseBuilding = buildingService.find(1L, 1L);
        assertTrue(responseBuilding.isPresent());
    }
}
