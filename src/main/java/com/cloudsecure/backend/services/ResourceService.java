package com.cloudsecure.backend.services;

import com.cloudsecure.backend.models.Resource;
import com.cloudsecure.backend.repositories.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository repo;

    public ResourceService(ResourceRepository repo) {
        this.repo = repo;
    }

    public List<Resource> getAllResources() {
        return repo.findAll();
    }

    // Optionnel: ajouter une ressource
    public Resource addResource(Resource resource) {
        return repo.save(resource);
    }
}
