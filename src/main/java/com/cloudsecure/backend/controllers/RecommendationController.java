package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.models.Recommendation;
import com.cloudsecure.backend.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        return ResponseEntity.ok(service.getAllRecommendations());
    }


    @PostMapping("/add")
    public ResponseEntity<Recommendation> addRecommendation(@RequestBody Recommendation recommendation) {
        return ResponseEntity.ok(service.saveRecommendation(recommendation));
    }

    @PostMapping("/add-multiple")
    public ResponseEntity<List<Recommendation>> addMultiple(@RequestBody List<Recommendation> recommendations) {
        return ResponseEntity.ok(service.saveAll(recommendations));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Recommendation> updateRecommendation(
            @PathVariable Long id,
            @RequestBody Recommendation recommendation) {

        Recommendation updated = service.updateRecommendation(id, recommendation);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        boolean deleted = service.deleteRecommendation(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}

