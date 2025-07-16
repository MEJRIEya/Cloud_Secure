package com.cloudsecure.backend.services;

import com.cloudsecure.backend.models.Recommendation;
import com.cloudsecure.backend.repositories.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final RecommendationRepository repo;

    public RecommendationService(RecommendationRepository repo) {
        this.repo = repo;
    }

    public List<Recommendation> getRecommendationsForCategories(List<String> riskCategories) {
        return riskCategories.stream()
                .flatMap(cat -> repo.findByCategory(cat).stream())
                .collect(Collectors.toList());
    }

    public Recommendation saveRecommendation(Recommendation recommendation) {
        return repo.save(recommendation);
    }

    public List<Recommendation> saveAll(List<Recommendation> recommendations) {
        return repo.saveAll(recommendations);
    }

}
