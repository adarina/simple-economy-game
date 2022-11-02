package com.ada.simpleeconomygamespringboot.resource.repository;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private UserRepository userRepository;

//    @Test
//    public void givenUser_whenFindByUserId_thenReturnsResource() {
//
//        User testUser = UserBuilder
//                .anUser()
//                .withUsername("tester")
//                .withPassword("tester")
//                .withRole("USER")
//                .buildUserEntity();
//
//        testUser = userRepository.save(testUser);
//
//        Resource testResourceMud = ResourceBuilder
//                .aResource()
//                .defaultBuildMudEntity(testUser);
//
//        Resource testResourceStone = ResourceBuilder
//                .aResource()
//                .defaultBuildStoneEntity(testUser);
//
//        Resource testResourceMeat = ResourceBuilder
//                .aResource()
//                .defaultBuildMeatEntity(testUser);
//
//        List<Resource> testResources = new ArrayList<>();
//        testResources.add(testResourceMud);
//        testResources.add(testResourceStone);
//        testResources.add(testResourceMeat);
//
//        testUser.setResources(testResources);
//
//        Resource respondResourceMud = resourceRepository.save(testResourceMud);
//        Resource respondResourceStone = resourceRepository.save(testResourceStone);
//        Resource respondResourceMeat = resourceRepository.save(testResourceMeat);
//
//        List<Resource> findResources = resourceRepository.findByUserId(testUser.getId());
//
//        assertNotNull(findResources);
//        assertEquals(respondResourceMud.getType(), findResources.get(0).getType());
//        assertEquals(respondResourceStone.getType(), findResources.get(1).getType());
//        assertEquals(respondResourceMeat.getType(), findResources.get(2).getType());
//        assertEquals(respondResourceMud.getUser(), findResources.get(0).getUser());
//    }

//    @Test
//    public void givenUser_whenExistsResourceByUserId_thenReturnsTrue() {
//
//        User testUser = UserBuilder
//                .anUser()
//                .withUsername("tester")
//                .withPassword("tester")
//                .withRole("USER")
//                .buildUserEntity();
//
//        Resource testResource = ResourceBuilder
//                .aResource()
//                .defaultBuildMudEntity(testUser);
//
//        testUser = userRepository.save(testUser);
//        resourceRepository.save(testResource);
//        boolean isExist =  resourceRepository.existsResourceByUserId(testUser.getId());
//        assertTrue(isExist);
//    }

//    @Test
//    public void givenUser_whenExistsResourceByNotExistingUserId_thenReturnsNull() {
//
//        User testUser = UserBuilder
//                .anUser()
//                .withUsername("tester")
//                .withPassword("tester")
//                .withRole("USER")
//                .buildUserEntity();
//
//        testUser = userRepository.save(testUser);
//
//        Resource testResource = ResourceBuilder
//                .aResource()
//                .defaultBuildMudEntity(testUser);
//
//        resourceRepository.save(testResource);
//        boolean isExist = resourceRepository.existsResourceByUserId(testUser.getId() + 1);
//        assertFalse(isExist);
//    }

//    @Test
//    public void givenUser_whenExistsResourceByUserId_thenReturnsFalse() {
//        boolean isExist = resourceRepository.existsResourceByUserId(1L);
//        assertFalse(isExist);
//    }

    @Test
    public void givenUser_whenFindByNotExistingUserIdAndType_thenReturnsNull() {
        Resource findResource = resourceRepository.findByUserIdAndType(1L, "MUD");
        assertNull(findResource);
    }
}
