package com.cloudsecure.backend.repositories;


import com.cloudsecure.backend.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    // On peut ajouter des méthodes spécifiques si besoin plus tard
}
