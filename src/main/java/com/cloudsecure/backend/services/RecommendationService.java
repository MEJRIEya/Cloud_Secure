package com.cloudsecure.backend.services;

import com.cloudsecure.backend.models.Recommendation;
import com.cloudsecure.backend.repositories.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RecommendationRepository repository;

    public RecommendationService(RecommendationRepository repository) {
        this.repository = repository;
    }

    public List<Recommendation> getRecommendationsForCategories(List<String> riskCategories) {
        return riskCategories.stream()
                .flatMap(cat -> repository.findByCategory(cat).stream())
                .collect(Collectors.toList());
    }

    public Recommendation saveRecommendation(Recommendation recommendation) {
        return repository.save(recommendation);
    }

    public List<Recommendation> saveAll(List<Recommendation> recommendations) {
        return repository.saveAll(recommendations);
    }

    public List<Recommendation> getAllRecommendations() {
        return repository.findAll();
    }

    public Recommendation updateRecommendation(Long id, Recommendation newRec) {
        return repository.findById(id).map(existing -> {
            existing.setCategory(newRec.getCategory());
            existing.setMessage(newRec.getMessage());
            // mettre à jour d'autres champs si besoin
            return repository.save(existing);
        }).orElse(null);
    }

//    public Recommendation getRecommendationById(Long id) {
//        return repository.findById(id).orElse(null);
//    }

    public boolean deleteRecommendation(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
