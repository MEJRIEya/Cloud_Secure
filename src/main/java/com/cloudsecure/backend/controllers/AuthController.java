package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.dtos.UserRegisterDTO;
import com.cloudsecure.backend.models.User;
import com.cloudsecure.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO dto) {
        User createdUser = userService.register(dto);
        return ResponseEntity.ok(createdUser);
    }
}
