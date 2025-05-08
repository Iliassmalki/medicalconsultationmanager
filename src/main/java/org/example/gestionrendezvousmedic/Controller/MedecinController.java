package org.example.gestionrendezvousmedic.Controller;

import org.example.gestionrendezvousmedic.dtos.MedecinDashboardDto;
import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.repos.MedecinRepository;
import org.example.gestionrendezvousmedic.services.MedecinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medecin")
@PreAuthorize("hasRole('ROLE_MEDECIN')")
public class MedecinController {
    private static final Logger logger = LoggerFactory.getLogger(MedecinController.class);

    @Autowired
    private MedecinService medecinService;
    @Autowired
    private MedecinRepository medecinRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<MedecinDashboardDto> getMedecinDashboard(Authentication authentication) {
        logger.info("Fetching dashboard for user: {}", authentication.getName());
        String email = authentication.getName(); // Email from JWT

        Medecin medecin = medecinRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Medecin not found for email: {}", email);
                    return new RuntimeException("Medecin not found");
                });

        MedecinDashboardDto dashboard = medecinService.getDashboardData(medecin.getId());
        return ResponseEntity.ok(dashboard);
    }
}