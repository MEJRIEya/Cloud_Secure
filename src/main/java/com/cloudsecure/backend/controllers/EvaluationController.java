package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.dtos.UserResponseDTO;
import com.cloudsecure.backend.models.Evaluation;
import com.cloudsecure.backend.models.Question;
import com.cloudsecure.backend.repositories.QuestionRepository;
import com.cloudsecure.backend.services.EvaluationService;
import com.cloudsecure.backend.config.JwtUtil;
import com.cloudsecure.backend.services.UserService;
import com.cloudsecure.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final QuestionRepository questionRepo;
    private final UserService userService;

    // Injection via constructeur (bonne pratique)
    @Autowired
    public EvaluationController(EvaluationService evaluationService, QuestionRepository questionRepo, UserService userService) {
        this.evaluationService = evaluationService;
        this.questionRepo = questionRepo;
        this.userService = userService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitEvaluation(
            @RequestBody List<UserResponseDTO> responses,
            @RequestHeader("Authorization") String authorization) {
        String email = JwtUtil.extractEmail(authorization.replace("Bearer ", ""));
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Evaluation result = evaluationService.createEvaluation(user.getId(), responses);

        // renvoyer un JSON qui contient explicitement le score
        return ResponseEntity.ok(
                java.util.Map.of(
                        "totalScore", result.getTotalScore(), // ⚡ tu dois avoir ce champ dans Evaluation
                        "evaluationId", result.getId()
                )
        );
    }

    // User-scoped: derive user from JWT, no userId param needed
    @PostMapping("/submit/me")
    public ResponseEntity<?> submitEvaluationForCurrentUser(
            @RequestBody List<UserResponseDTO> responses,
            @RequestHeader("Authorization") String authorization) {
        String email = JwtUtil.extractEmail(authorization.replace("Bearer ", ""));
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Evaluation result = evaluationService.createEvaluation(user.getId(), responses);
        return ResponseEntity.ok(
                java.util.Map.of(
                        "totalScore", result.getTotalScore(),
                        "evaluationId", result.getId()
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyEvaluations(@RequestHeader("Authorization") String authorization) {
        String email = JwtUtil.extractEmail(authorization.replace("Bearer ", ""));
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<Evaluation> list = evaluationService.getByUserId(user.getId());
        return ResponseEntity.ok(list);
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
