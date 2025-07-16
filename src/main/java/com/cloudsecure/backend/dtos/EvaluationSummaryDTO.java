package com.cloudsecure.backend.dtos;


import java.time.LocalDateTime;

public class EvaluationSummaryDTO {
    private int score;
    private LocalDateTime date;

    public EvaluationSummaryDTO(int score, LocalDateTime date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

