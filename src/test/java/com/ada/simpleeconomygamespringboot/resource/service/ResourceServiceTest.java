package com.ada.simpleeconomygamespringboot.resource.service;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.entity.ResourceBuilder;
import com.ada.simpleeconomygamespringboot.resource.repository.ResourceRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void givenExistedUser_whenCreate_thenReturnsThat() {

        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        when(resourceRepository.save(any(Resource.class))).thenReturn(testResourceMud);
        Resource createdResource = resourceService.create(testResourceMud);

        assertThat(createdResource.getType()).isSameAs(testResourceMud.getType());
    }

    @Test
    public void givenNotExistedUser_whenCreate_thenReturnsNull() {

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(null);

        when(resourceRepository.save(any(Resource.class))).thenReturn(null);
        Resource createdResource = resourceService.create(testResourceMud);
        assertNull(createdResource);
    }

    @Test
    public void givenNotExistedUser_whenFindAll_thenReturnsNull() {
        when(resourceRepository.findAllByUser(null)).thenReturn(null);
        List<Resource> responseBuilding = resourceService.findAll(null);
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

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(testUser, 1L);

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResourceMud);
        testUser.setResources(testResources);

        when(resourceRepository.findAllByUser(testUser)).thenReturn(testResources);
        List<Resource> responseBuilding = resourceService.findAll(testUser);
        assertNotNull(responseBuilding);
    }

    @Test
    public void givenExistedUserIdAndResourceMud_whenChangeMudQuantity_thenReturnsResourceAndProperChange() throws NoSuchFieldException, IllegalAccessException {
        User testUser = UserBuilder
                .anUser()
                .withUsername("tester")
                .withPassword("tester")
                .withRole("USER")
                .buildUserEntity();

        Field privateIdField = User.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testUser, 1L);

        Resource testResourceMud = ResourceBuilder
                .aResource()
                .defaultBuildMudEntity(testUser);

        privateIdField = Resource.class.getDeclaredField("id");
        privateIdField.setAccessible(true);
        privateIdField.set(testResourceMud, 1L);

        List<Resource> testResources = new ArrayList<>();
        testResources.add(testResourceMud);
        testUser.setResources(testResources);

        when(resourceRepository.findByUserIdAndType(testUser.getId(), testResourceMud.getType())).thenReturn(testResourceMud);
        when(resourceRepository.save(any())).thenReturn(testResourceMud);

        resourceService.changeMudQuantity(1L, 100L);

        when(resourceRepository.findAllByUser(testUser)).thenReturn(testResources);
        List<Resource> responseResource = resourceService.findAll(testUser);

        assertNotNull(responseResource);
        assertEquals(responseResource.get(0).getId(), testResourceMud.getId());
        assertEquals(responseResource.get(0).getType(), testResourceMud.getType());
        assertEquals(responseResource.get(0).getAmount(),testResourceMud.getAmount());
        assertEquals(responseResource.get(0).getUser(), testResourceMud.getUser());
    }
}
