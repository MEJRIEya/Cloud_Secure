package com.cloudsecure.backend.dtos;

import java.util.List;

public class DashboardDTO {
    private int totalEvaluations;
    private double averageScore;
    private List<EvaluationSummaryDTO> history;

    public DashboardDTO(int totalEvaluations, double averageScore, List<EvaluationSummaryDTO> history) {
        this.totalEvaluations = totalEvaluations;
        this.averageScore = averageScore;
        this.history = history;
    }

    public DashboardDTO() {
        // constructeur vide requis par défaut si tu veux faire `new DashboardDTO()`
    }


    public int getTotalEvaluations() {
        return totalEvaluations;
    }
    public void setTotalEvaluations(int totalEvaluations) {
        this.totalEvaluations = totalEvaluations;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {

        this.averageScore = averageScore;

    }

    public List<EvaluationSummaryDTO> getHistory() {

        return history;
    }
    public void setHistory(List<EvaluationSummaryDTO> history) {

        this.history = history;
    }

}
