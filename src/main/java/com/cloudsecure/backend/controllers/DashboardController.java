package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.dtos.DashboardDTO;
import com.cloudsecure.backend.services.DashboardService;
import com.cloudsecure.backend.config.JwtUtil;
import com.cloudsecure.backend.services.UserService;
import com.cloudsecure.backend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final UserService userService;

    public DashboardController(DashboardService dashboardService, UserService userService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDashboard(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {
        String email = JwtUtil.extractEmail(authorization.replace("Bearer ", ""));
        User current = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        boolean isSelf = current.getId().equals(id);
        boolean isAdmin = current.getRole() != null && current.getRole().name().equals("ADMIN");
        if (!isSelf && !isAdmin) {
            return ResponseEntity.status(403).body("Forbidden");
        }
        return ResponseEntity.ok(dashboardService.getUserDashboard(id));
    }

    @GetMapping("/me")
    public ResponseEntity<DashboardDTO> getMyDashboard(@RequestHeader("Authorization") String authorization) {
        String email = JwtUtil.extractEmail(authorization.replace("Bearer ", ""));
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(dashboardService.getUserDashboard(user.getId()));
    }
}
