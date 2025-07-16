package com.cloudsecure.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Recommendation {
    @Id
    @GeneratedValue
    private Long id;

    private String category; // Ex : Authentification, Partage
    private String message;  // Ex : "Utilisez l'authentification à deux facteurs..."

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getters & Setters
}
