package com.cloudsecure.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String category; // exemple : Authentification, Partage, etc.
    private int riskWeight; // Score à ajouter si mauvaise réponse

    public Long getId() {
        return id;
    }

    public int getRiskWeight() {
        return riskWeight;
    }

    public String getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRiskWeight(int riskWeight) {
        this.riskWeight = riskWeight;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setText(String text) {
        this.text = text;
    }
}
