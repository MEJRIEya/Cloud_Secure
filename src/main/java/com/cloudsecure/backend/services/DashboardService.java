package com.cloudsecure.backend.services;

import com.cloudsecure.backend.dtos.DashboardDTO;
import com.cloudsecure.backend.dtos.EvaluationSummaryDTO;
import com.cloudsecure.backend.models.Evaluation;
import com.cloudsecure.backend.repositories.EvaluationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final EvaluationRepository repo;

    public DashboardService(EvaluationRepository repo) {
        this.repo = repo;
    }

    public DashboardDTO getUserDashboard(Long userId) {
        List<Evaluation> evaluations = repo.findByUserId(userId);

        int total = evaluations.size();
        double avg = total > 0 ? evaluations.stream().mapToInt(Evaluation::getTotalScore).average().orElse(0) : 0;

        List<EvaluationSummaryDTO> history = evaluations.stream()
                .map(e -> new EvaluationSummaryDTO(e.getTotalScore(), e.getDateEvaluation()))
                .collect(Collectors.toList());

        DashboardDTO dto = new DashboardDTO();
        dto.setTotalEvaluations(total);
        dto.setAverageScore(avg);
        dto.setHistory(history);

        return dto;
    }
}
