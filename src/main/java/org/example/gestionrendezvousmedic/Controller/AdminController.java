package org.example.gestionrendezvousmedic.Controller;

import org.example.gestionrendezvousmedic.dtos.AdminDashboardDto;
import org.example.gestionrendezvousmedic.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardDto> getAdminDashboard() {
        AdminService adminService = new AdminService();
        AdminDashboardDto dashboard = adminService.getDashboardData();
        return ResponseEntity.ok(dashboard);
    }
}
