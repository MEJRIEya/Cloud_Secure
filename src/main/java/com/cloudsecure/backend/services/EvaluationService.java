package com.cloudsecure.backend.services;

import com.cloudsecure.backend.dtos.UserResponseDTO;
import com.cloudsecure.backend.models.Answer;
import com.cloudsecure.backend.models.Evaluation;
import com.cloudsecure.backend.models.Question;
import com.cloudsecure.backend.repositories.EvaluationRepository;
import com.cloudsecure.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private EvaluationRepository evaluationRepo;

    public Evaluation createEvaluation(Long userId, List<UserResponseDTO> responses) {
        int totalScore = 0;
        Evaluation eval = new Evaluation();
        eval.setUserId(userId);
        eval.setSubmittedAt(LocalDateTime.now());

        List<Answer> answers = new ArrayList<>();

        for (UserResponseDTO dto : responses) {
            Question question = questionRepo.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question non trouvée avec ID : " + dto.getQuestionId()));

            boolean isRisky = evaluateRisk(dto.getResponse(), question);
            if (isRisky) totalScore += question.getRiskWeight();

            Answer answer = new Answer();
            answer.setEvaluation(eval);
            answer.setQuestion(question);
            answer.setResponse(dto.getResponse());
            answer.setIsRisky(isRisky);

            answers.add(answer);
        }

        eval.setAnswers(answers);
        eval.setTotalScore(totalScore);

        return evaluationRepo.save(eval);
    }

    private boolean evaluateRisk(String response, Question question) {
        // logique simple : par ex. "non" = risque
        return response.equalsIgnoreCase("non");
    }
}

