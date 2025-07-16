package com.cloudsecure.backend.dtos;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephonne;
    private String role;

}