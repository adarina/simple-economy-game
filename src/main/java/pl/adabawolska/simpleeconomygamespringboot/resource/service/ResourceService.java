package pl.adabawolska.simpleeconomygamespringboot.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.resource.repository.ResourceRepository;
import pl.adabawolska.simpleeconomygamespringboot.user.repository.UserRepository;

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

    public Optional<Resource> find(Long id) {
        return resourceRepository.findById(id);
    }

    @Transactional
    public Resource create(Resource resource) {
        return resourceRepository.save(resource);
    }
}
