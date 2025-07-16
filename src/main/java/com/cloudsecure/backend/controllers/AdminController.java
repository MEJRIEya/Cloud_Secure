package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.models.Evaluation;
import com.cloudsecure.backend.repositories.EvaluationRepository;
import com.cloudsecure.backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import com.cloudsecure.backend.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final EvaluationRepository evaluationRepo;

    public AdminController(UserRepository userRepo, EvaluationRepository evaluationRepo) {
        this.userRepo = userRepo;
        this.evaluationRepo = evaluationRepo;
    }

    // ✅ Liste de tous les utilisateurs
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    // ✅ Liste de toutes les évaluations
    @GetMapping("/evaluations")
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        return ResponseEntity.ok(evaluationRepo.findAll());
    }

    // ✅ Supprimer un utilisateur
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Supprimer une évaluation
    @DeleteMapping("/evaluations/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
