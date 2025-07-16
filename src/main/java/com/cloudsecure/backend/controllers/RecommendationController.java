package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.models.Recommendation;
import com.cloudsecure.backend.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<List<Recommendation>> getRecommendations(@RequestBody List<String> categories) {
        return ResponseEntity.ok(service.getRecommendationsForCategories(categories));
    }

    @PostMapping("/add")
    public ResponseEntity<Recommendation> addRecommendation(@RequestBody Recommendation recommendation) {
        return ResponseEntity.ok(service.saveRecommendation(recommendation));
    }
    @PostMapping("/add-multiple")
    public ResponseEntity<List<Recommendation>> addMultiple(@RequestBody List<Recommendation> recommendations) {
        return ResponseEntity.ok(service.saveAll(recommendations));
    }


}

