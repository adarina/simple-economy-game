package com.ada.simpleeconomygamespringboot.unit.service;

import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
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
public class UnitServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @InjectMocks
    private UnitService unitService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void givenExistedUserId_whenFind_thenReturnsUnitAndProperValues() throws NoSuchFieldException, IllegalAccessException {
        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        privateIdField = Unit.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUnitGoblinArcher, 1L);

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testUser));
        when(unitRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.ofNullable(testUnitGoblinArcher));

        Optional<Unit> responseUnit = unitService.find(1L, 1L);

        assertTrue(responseUnit.isPresent());
        assert testUnitGoblinArcher != null;
        assertEquals(responseUnit.get().getType(), testUnitGoblinArcher.getType());
        assertEquals(responseUnit.get().getActive(),testUnitGoblinArcher.getActive());
        assertEquals(responseUnit.get().getUser(), testUnitGoblinArcher.getUser());
        assertEquals(responseUnit.get().getAmount(), testUnitGoblinArcher.getAmount());
    }

    @Test
    public void givenNotExistedUserId_whenFind_thenReturnsFalse() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Unit> responseUnit = unitService.find(1L, 1L);
        assertFalse(responseUnit.isPresent());
    }

    @Test
    public void givenExistedUserIdAndUnitGoblinArcher_whenChangeGoblinArcherActivity_thenReturnsUnitAndProperChange() throws NoSuchFieldException, IllegalAccessException {
        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        privateIdField = Unit.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUnitGoblinArcher, 1L);

        when(unitRepository.findByUserIdAndType(testUser.getId(), testUnitGoblinArcher.getType())).thenReturn(testUnitGoblinArcher);
        when(unitRepository.save(any())).thenReturn(testUnitGoblinArcher);

        unitService.changeGoblinArcherActivity(1L, true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(unitRepository.findByIdAndUser(1L, testUser)).thenReturn(Optional.of(testUnitGoblinArcher));

        Optional<Unit> responseUnit = unitService.find(testUser.getId(), testUnitGoblinArcher.getId());

        assertTrue(responseUnit.isPresent());
        assertEquals(responseUnit.get().getId(), testUnitGoblinArcher.getId());
        assertEquals(responseUnit.get().getType(), testUnitGoblinArcher.getType());
        assertEquals(responseUnit.get().getActive(),testUnitGoblinArcher.getActive());
        assertEquals(responseUnit.get().getUser(), testUnitGoblinArcher.getUser());
    }

    @Test
    public void givenExistedUserId_whenCreate_thenReturnsThat() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        when(unitRepository.save(any(Unit.class))).thenReturn(testUnitGoblinArcher);
        Unit createdUnit = unitService.create(testUnitGoblinArcher);

        assertThat(createdUnit.getType()).isSameAs(testUnitGoblinArcher.getType());
    }

    @Test
    public void givenNotExistedUser_whenCreate_thenReturnsNull() {

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(null);

        when(unitRepository.save(any(Unit.class))).thenReturn(null);
        Unit createdUnit = unitService.create(testUnitGoblinArcher);
        assertNull(createdUnit);
    }

    @Test
    public void givenNotExistedUser_whenFindAll_thenReturnsNull() {
        when(unitRepository.findAllByUser(null)).thenReturn(null);
        List<Unit> responseUnit = unitService.findAll(null);
        assertNull(responseUnit);
    }

    @Test
    public void givenExistedUser_whenFindAll_thenReturnsNotNull() throws NoSuchFieldException, IllegalAccessException {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        List<Unit> testUnits = new ArrayList<>();
        testUnits.add(testUnitGoblinArcher);
        testUser.setUnits(testUnits);

        when(unitRepository.findAllByUser(testUser)).thenReturn(testUnits);
        List<Unit> responseUnit = unitService.findAll(testUser);
        assertNotNull(responseUnit);
    }
}
