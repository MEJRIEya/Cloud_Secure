package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.config.JwtUtil;
import com.cloudsecure.backend.dtos.LoginDTO;
import com.cloudsecure.backend.dtos.UserRegisterDTO;
import com.cloudsecure.backend.models.User;
import com.cloudsecure.backend.services.UserService;
import com.cloudsecure.backend.services.MailService;
import com.cloudsecure.backend.services.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;
    @Autowired
    private RecaptchaService recaptchaService;

    @org.springframework.beans.factory.annotation.Value("${app.frontend.loginUrl}")
    private String frontendLoginUrl;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO dto) {
        // reCAPTCHA validation
        boolean captchaOk = recaptchaService.verify(dto.getRecaptchaToken(), null);
        if (!captchaOk) {
            throw new RuntimeException("Invalid reCAPTCHA");
        }
        User createdUser = userService.register(dto);
        // Send verification email
        try {
            // token created in register
            mailService.sendVerificationEmail(createdUser.getEmail(), createdUser.getEmailVerificationToken());
        } catch (Exception e) {
            // Log only, do not fail registration
            System.out.println("Failed to send verification email: " + e.getMessage());
        }
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Optional<User> userOpt = userService.findByEmail(dto.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                String token = JwtUtil.generateToken(user.getEmail());

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("nom", user.getNom());
                response.put("message", "Connexion réussie");

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Email ou mot de passe invalide");
    }


    @GetMapping("/all")
    public List<User> all() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserRegisterDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    // Mise à jour de profil (utilise le token JWT pour trouver l'ID)
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserRegisterDTO dto, @RequestHeader("Authorization") String token) {
        try {
            // Extraire l'email du token JWT
            String email = JwtUtil.extractEmail(token.replace("Bearer ", ""));
            User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

            // Utiliser l'ID de l'utilisateur connecté
            Long userId = user.getId();
            System.out.println("=== UPDATE PROFILE DEBUG ===");
            System.out.println("User ID: " + userId);
            System.out.println("User email: " + email);
            System.out.println("New data: " + dto.getNom() + " " + dto.getPrenom());

            // Mettre à jour via la méthode existante qui utilise l'ID
            User updatedUser = userService.update(userId, dto);
            System.out.println("✅ Profile updated successfully");
            System.out.println("=== END UPDATE PROFILE DEBUG ===");

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            System.out.println("❌ Error updating profile: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // Mot de passe oublié
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            User user = userService.createResetToken(email);
            mailService.sendPasswordResetEmail(user.getEmail(), user.getResetToken());
        } catch (Exception e) {
            // on ne révèle pas si l'email existe pour la confidentialité
        }
        return ResponseEntity.ok(Map.of("message", "Si le compte existe, un email a été envoyé."));
    }

    // Réinitialiser le mot de passe
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé."));
    }

    // Email verification endpoint
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.status(302)
                .header("Location", frontendLoginUrl)
                .build();
    }

    // Public endpoint to resend verification email
    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestParam String email) {
        String token = userService.createOrRefreshVerificationToken(email);
        mailService.sendVerificationEmail(email, token);
        return ResponseEntity.ok(Map.of("message", "Email de vérification renvoyé"));
    }
}
