package com.ada.simpleeconomygamespringboot.unit.repository;

import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.entity.UnitBuilder;
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
public class UnitRepositoryTest {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUser_whenFindByUserId_thenReturnsUnit() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Unit testUnitGoblinArcher = UnitBuilder
                .anUnit()
                .defaultBuildGoblinArcherEntity(testUser);

        Unit testUnitOrcWarrior = UnitBuilder
                .anUnit()
                .defaultBuildOrcWarriorEntity(testUser);

        Unit testIUnitUglyTroll = UnitBuilder
                .anUnit().defaultBuildUglyTrollEntity(testUser);


        List<Unit> testUnits = new ArrayList<>();
        testUnits.add(testUnitGoblinArcher);
        testUnits.add(testIUnitUglyTroll);
        testUnits.add(testUnitOrcWarrior);

        testUser.setUnits(testUnits);

        Unit respondUnitGoblinArcher = unitRepository.save(testUnitGoblinArcher);
        Unit respondUnitUglyTroll = unitRepository.save(testIUnitUglyTroll);
        Unit respondUnitOrcWarrior = unitRepository.save(testUnitOrcWarrior);

        List<Unit> findUnits = unitRepository.findByUserId(testUser.getId());

        assertNotNull(findUnits);
        assertEquals(respondUnitGoblinArcher.getType(), findUnits.get(0).getType());
        assertEquals(respondUnitUglyTroll.getType(), findUnits.get(1).getType());
        assertEquals(respondUnitOrcWarrior.getType(), findUnits.get(2).getType());
        assertEquals(respondUnitGoblinArcher.getUser(), findUnits.get(0).getUser());
    }

    @Test
    public void givenUser_whenExistsByUserId_thenReturnsFalse() {
        boolean isExist = unitRepository.existsById(1L);
        assertFalse(isExist);
    }

    @Test
    public void givenUser_whenFindByNotExistingUserIdAndType_thenReturnsNull() {
        Unit findUnit = unitRepository.findByUserIdAndType(1L, "ARCHER");
        assertNull(findUnit);
    }
}
