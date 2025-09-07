package com.cloudsecure.backend.models;

import jakarta.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private String response; // réponse de l'utilisateur (oui/non/etc.)
    private boolean isRisky; // réponse mauvaise = true

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // getters, setters

    public Long getId() {
        return id;
    }

    public boolean isRisky() {
        return isRisky;
    }

    public String getResponse() {
        return response;
    }

    public Question getQuestion() {
        return question;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsRisky(boolean risky) {
        isRisky = risky;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
