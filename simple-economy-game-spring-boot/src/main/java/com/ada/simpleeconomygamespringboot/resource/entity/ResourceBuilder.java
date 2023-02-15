package com.ada.simpleeconomygamespringboot.resource.entity;

import com.ada.simpleeconomygamespringboot.user.entity.User;

public class ResourceBuilder {

    private ResourceBuilder() {
    }

    public static ResourceBuilder aResource() {
        return new ResourceBuilder();
    }

    public Resource defaultBuildMudEntity(User user) {
        Resource resource = new Resource();
        resource.setType("MUD");
        resource.setAmount(9000L);
        resource.setUser(user);
        return resource;
    }

    public Resource defaultBuildStoneEntity(User user) {
        Resource resource = new Resource();
        resource.setType("STONE");
        resource.setAmount(5000L);
        resource.setUser(user);
        return resource;
    }

    public Resource defaultBuildMeatEntity(User user) {
        Resource resource = new Resource();
        resource.setType("MEAT");
        resource.setAmount(6000L);
        resource.setUser(user);
        return resource;
    }
}