package com.cloudsecure.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Evaluation evaluation;

    @ManyToOne
    private Question question;

    private String response; // réponse de l'utilisateur (oui/non/etc.)
    private boolean isRisky; // réponse mauvaise = true

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
