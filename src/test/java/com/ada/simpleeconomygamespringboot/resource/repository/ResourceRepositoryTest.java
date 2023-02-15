package com.ada.simpleeconomygamespringboot.resource.repository;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.entity.UserBuilder;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUser_whenFindByUserId_thenReturnsResource() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Resource testResourceStone = ResourceBuilder
                .aResource()
                .defaultBuildStoneEntity(testUser);

        Resource testResourceMeat = ResourceBuilder
                .aResource()
                .defaultBuildMeatEntity(testUser);

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResourceMud);
        testResources.add(testResourceStone);
        testResources.add(testResourceMeat);

        testUser.setResources(testResources);

        Resource respondResourceMud = resourceRepository.save(testResourceMud);
        Resource respondResourceStone = resourceRepository.save(testResourceStone);
        Resource respondResourceMeat = resourceRepository.save(testResourceMeat);

        List<Resource> findResources = resourceRepository.findByUserId(testUser.getId());

        assertNotNull(findResources);
        assertEquals(respondResourceMud.getType(), findResources.get(0).getType());
        assertEquals(respondResourceStone.getType(), findResources.get(1).getType());
        assertEquals(respondResourceMeat.getType(), findResources.get(2).getType());
        assertEquals(respondResourceMud.getUser(), findResources.get(0).getUser());
    }

    @Test
    public void givenUserAndResource_whenSaveResource_thenResourceIsPersisted() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Resource testResource = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Resource savedResource = resourceRepository.save(testResource);

        assertNotNull(savedResource.getId());
        assertEquals(testResource.getType(), savedResource.getType());
        assertEquals(testResource.getAmount(), savedResource.getAmount());
        assertEquals(testResource.getUser(), savedResource.getUser());
    }

    @Test
    public void givenUserIdAndResourceType_whenFindByUserIdAndType_thenReturnsResource() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Resource testResource = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Resource savedResource = resourceRepository.save(testResource);

        Resource foundResource = resourceRepository.findByUserIdAndType(testUser.getId(), testResource.getType());

        assertNotNull(foundResource);
        assertEquals(savedResource.getId(), foundResource.getId());
        assertEquals(savedResource.getType(), foundResource.getType());
        assertEquals(savedResource.getAmount(), foundResource.getAmount());
        assertEquals(savedResource.getUser(), foundResource.getUser());
    }

    @Test
    public void givenUserId_whenFindByUserId_thenReturnsListOfResources() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Resource testResourceStone = ResourceBuilder
                .aResource()
                .defaultBuildStoneEntity(testUser);

        Resource testResourceMeat = ResourceBuilder
                .aResource()
                .defaultBuildMeatEntity(testUser);

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResourceMud);
        testResources.add(testResourceStone);
        testResources.add(testResourceMeat);

        testUser.setResources(testResources);

        resourceRepository.saveAll(testResources);

        List<Resource> foundResources = resourceRepository.findByUserId(testUser.getId());

        assertNotNull(foundResources);
        assertEquals(3, foundResources.size());
    }


    @Test
    public void givenUser_whenFindAllByUser_thenReturnsListOfResources() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        testUser = userRepository.save(testUser);

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Resource testResourceStone = ResourceBuilder
                .aResource()
                .defaultBuildStoneEntity(testUser);

        Resource testResourceMeat = ResourceBuilder
                .aResource()
                .defaultBuildMeatEntity(testUser);

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResourceMud);
        testResources.add(testResourceStone);
        testResources.add(testResourceMeat);

        testUser.setResources(testResources);

        resourceRepository.saveAll(testResources);

        List<Resource> foundResources = resourceRepository.findAllByUser(testUser);

        assertEquals(3, foundResources.size());
        assertThat(foundResources, hasItems(testResourceMud, testResourceStone, testResourceMeat));
    }


    @Test
    public void givenUser_whenFindByNotExistingUserIdAndType_thenReturnsNull() {
        Resource findResource = resourceRepository.findByUserIdAndType(2L, "MUD");
        assertNull(findResource);
    }
}
