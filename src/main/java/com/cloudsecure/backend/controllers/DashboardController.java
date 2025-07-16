package com.cloudsecure.backend.controllers;

import com.cloudsecure.backend.dtos.DashboardDTO;
import com.cloudsecure.backend.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<DashboardDTO> getUserDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getUserDashboard(id));
    }
}
