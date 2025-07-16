package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.dtos.UserResponseDTO;
import com.cloudsecure.backend.models.Evaluation;
import com.cloudsecure.backend.models.Question;
import com.cloudsecure.backend.repositories.QuestionRepository;
import com.cloudsecure.backend.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final QuestionRepository questionRepo;

    // Injection via constructeur (bonne pratique)
    @Autowired
    public EvaluationController(EvaluationService evaluationService, QuestionRepository questionRepo) {
        this.evaluationService = evaluationService;
        this.questionRepo = questionRepo;
    }

    @PostMapping("/submit")
    public ResponseEntity<Evaluation> submitEvaluation(
            @RequestBody List<UserResponseDTO> responses,
            @RequestParam Long userId) {
        Evaluation result = evaluationService.createEvaluation(userId, responses);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/questions")
    public ResponseEntity<?> getAllQuestions() {
        try {
            List<Question> questions = questionRepo.findAll();
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }


}
