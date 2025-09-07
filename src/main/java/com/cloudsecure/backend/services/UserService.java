package com.cloudsecure.backend.services;

import com.cloudsecure.backend.dtos.LoginDTO;
import com.cloudsecure.backend.dtos.UserRegisterDTO;
import com.cloudsecure.backend.models.Role;
import com.cloudsecure.backend.models.User;
import com.cloudsecure.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.time.Duration;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setTelephonne(dto.getTelephonne());
        user.setRole(Role.USER);

        // init verify
        user.setEmailVerified(false);
        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerificationExpiry(Instant.now().plus(Duration.ofDays(1)));

        return userRepository.save(user);
    }

    public boolean login(LoginDTO dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        if (user == null) return false;
        return passwordEncoder.matches(dto.getPassword(), user.getPassword());
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(Long id, UserRegisterDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setTelephonne(dto.getTelephonne());
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setRole(Role.valueOf(dto.getRole()));
        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createResetToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(Instant.now().plus(Duration.ofHours(1)));
        return userRepository.save(user);
    }

    public void resetPassword(String token, String newPassword) {
        System.out.println("=== RESET PASSWORD DEBUG ===");
        System.out.println("Token reçu: " + token);
        
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            System.out.println("❌ Token non trouvé dans la base de données");
            throw new RuntimeException("Invalid token");
        }
        
        User user = userOpt.get();
        System.out.println("✅ Utilisateur trouvé: " + user.getEmail());
        System.out.println("Token expiry: " + user.getResetTokenExpiry());
        System.out.println("Maintenant: " + Instant.now());
        
        if (user.getResetTokenExpiry() == null || Instant.now().isAfter(user.getResetTokenExpiry())) {
            System.out.println("❌ Token expiré");
            throw new RuntimeException("Token expired");
        }
        
        System.out.println("✅ Token valide, mise à jour du mot de passe");
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        System.out.println("✅ Mot de passe mis à jour avec succès");
        System.out.println("=== END RESET PASSWORD DEBUG ===");
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token).orElseThrow(() -> new RuntimeException("Invalid verification token"));
        if (user.getEmailVerificationExpiry() == null || Instant.now().isAfter(user.getEmailVerificationExpiry())) {
            throw new RuntimeException("Verification token expired");
        }
        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiry(null);
        userRepository.save(user);
    }

    public String createOrRefreshVerificationToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmailVerified(false);
        String token = UUID.randomUUID().toString();
        user.setEmailVerificationToken(token);
        user.setEmailVerificationExpiry(Instant.now().plus(Duration.ofDays(1)));
        userRepository.save(user);
        return token;
    }

}
