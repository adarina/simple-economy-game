package com.ada.simpleeconomygamespringboot.unit.service;

import com.ada.simpleeconomygamespringboot.building.repository.BuildingRepository;
import com.ada.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import com.ada.simpleeconomygamespringboot.resource.service.ResourceService;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.ada.simpleeconomygamespringboot.unit.properties.UnitProperties;
import com.ada.simpleeconomygamespringboot.unit.repository.UnitRepository;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {

//    @TestConfiguration
//    static class ServiceTestContextConfiguration {
//
//
//
//
//        @Bean
//        public UnitService unitService() {
//            return new UnitService();
//        }
//    }

    @MockBean
    private UnitService unitService;

    @MockBean
    private UnitRepository unitRepository;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private UnitProperties unitProperties;

    @MockBean
    private BuildingRepository buildingRepository;

    @MockBean
    private ResourceRepository resourceRepository;

    @MockBean
    private UserRepository userRepository;





    @Test
    void givenNotExistedUserId_whenExistByUserID_thenFalse() {
        when(unitRepository.findByUserIdAndType(1L, "COTTAGE")).thenReturn(Unit.builder().build());

        Optional<Unit> unit = unitService.find(1L);

        assertNull( unit);
    }



    @Test
    void givenExistedUserId_whenExistByUserID_thenTrue() throws NoSuchFieldException, IllegalAccessException {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);


        UserRepository userRepository = mock(UserRepository.class);
        UnitService lol2 = mock(UnitService.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

       Optional<User> lol = userRepository.findById(1L);
        System.out.println(lol);
        boolean isExisted = lol2.existsByUserId(1L);
        System.out.println(isExisted);
        assertTrue(isExisted);
    }

}
