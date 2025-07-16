package com.cloudsecure.backend.repositories;

import com.cloudsecure.backend.models.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByCategory(String category);
}

