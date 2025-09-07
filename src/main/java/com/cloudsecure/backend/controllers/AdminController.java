package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.models.Evaluation;
import com.cloudsecure.backend.repositories.EvaluationRepository;
import com.cloudsecure.backend.repositories.UserRepository;
import com.cloudsecure.backend.services.QuestionService;
import com.cloudsecure.backend.services.MailService;
import org.springframework.http.ResponseEntity;
import com.cloudsecure.backend.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final EvaluationRepository evaluationRepo;
    private final QuestionService questionService;
    private final MailService mailService;

    public AdminController(UserRepository userRepo, EvaluationRepository evaluationRepo, QuestionService questionService, MailService mailService) {
        this.userRepo = userRepo;
        this.evaluationRepo = evaluationRepo;
        this.questionService = questionService;
        this.mailService = mailService;
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

    // ✅ Seed default questionnaire questions (idempotent)
    @PostMapping("/seed-questions")
    public ResponseEntity<?> seedQuestions() {
        int inserted = questionService.seedDefaultQuestions();
        return ResponseEntity.ok(java.util.Map.of(
                "inserted", inserted,
                "message", inserted == 0 ? "Questions déjà présentes" : "Questions ajoutées"
        ));
    }

    // ✅ Test d'envoi d'email (temporairement accessible à tous)
    @PostMapping("/test-email")
    public ResponseEntity<?> sendTestEmail(@RequestParam String to) {
        try {
            mailService.sendPasswordResetEmail(to, "test-token");
            return ResponseEntity.ok(java.util.Map.of("message", "Email de test envoyé", "to", to));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Map.of("error", e.getMessage(), "to", to));
        }
    }
}
