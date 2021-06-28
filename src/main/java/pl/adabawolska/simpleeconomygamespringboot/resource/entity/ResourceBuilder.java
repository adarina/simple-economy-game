package pl.adabawolska.simpleeconomygamespringboot.resource.entity;

import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

public class ResourceBuilder {

    private User user;

    private ResourceBuilder() {
    }

    public static ResourceBuilder aResource() {
        return new ResourceBuilder();
    }

    public Resource defaultBuild() {
        Resource resource = new Resource();
        resource.setMudQuantity(30000L);
        resource.setStoneQuantity(30000L);
        resource.setMeatQuantity(30000L);
        resource.setUser(user);
        return resource;
    }
}