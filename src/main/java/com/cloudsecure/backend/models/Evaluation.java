package com.cloudsecure.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId; // ou relation @ManyToOne avec User si tu as l'entité
    private int totalScore;
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    // getters, setters

    public Long getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public Long getUserId() {
        return userId;
    }
}
