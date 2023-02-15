package com.ada.simpleeconomygamespringboot.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import com.ada.simpleeconomygamespringboot.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    private final UserRepository userRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    public void changeMudQuantity(Long id, Long mudQtyToAdd) {
        Resource resource = resourceRepository.findByUserIdAndType(id, "MUD");
        if (resource.getAmount() + mudQtyToAdd < 0) {
            resource.setAmount(0L);
        } else {
            resource.setAmount(resource.getAmount() + mudQtyToAdd);
        }
        resourceRepository.save(resource);
    }

    public void changeStoneQuantity(Long id, Long stoneQtyToAdd) {
        Resource resource = resourceRepository.findByUserIdAndType(id, "STONE");
        if (resource.getAmount() + stoneQtyToAdd < 0) {
            resource.setAmount(0L);
        } else {
            resource.setAmount(resource.getAmount() + stoneQtyToAdd);
        }
        resourceRepository.save(resource);
    }

    @Transactional
    public Resource create(Resource resource) {
        return resourceRepository.save(resource);
    }

    public List<Resource> findAll(User user) {
        return resourceRepository.findAllByUser(user);
    }
}
