package org.example.gestionrendezvousmedic.Controller;

import org.example.gestionrendezvousmedic.dtos.MedecinDashboardDto;
import org.example.gestionrendezvousmedic.dtos.PatientDto;
import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.repos.MedecinRepository;
import org.example.gestionrendezvousmedic.services.MedecinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/updatePatient/{PatientId}")
    public ResponseEntity<PatientDto> updatePatient (Authentication authentication,
        @PathVariable Long PatientId,
                @RequestBody PatientDto updateDto) {
        String email = authentication.getName();
        Medecin medecin = medecinRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("Medecin not found"));

        PatientDto updatedPatient = medecinService.updatePatient(medecin.getId(), PatientId, updateDto);
        return ResponseEntity.ok(updatedPatient);
    }
    @PutMapping("/deletePatient/{PatientId}")
    public ResponseEntity<PatientDto> deletePatient (@PathVariable Long PatientId,
    Authentication authentication,
                                                     @RequestBody PatientDto deleteDto) {
        String email = authentication.getName();
        Medecin medecin = medecinRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("Medecin not found"));
        PatientDto deletedPatient = medecinService.deletePatient(medecin.getId(), PatientId);
        return ResponseEntity.ok(deletedPatient);

    }
    @PutMapping("/addPatient/createpatient")
    public ResponseEntity<PatientDto> CreatePatient(@RequestBody PatientDto addDto, Authentication authentication) {
        String email = authentication.getName();
        Medecin medecin = medecinRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("Medecin not found"));
        PatientDto addedPatient = medecinService.createPatient(medecin.getId(), addDto);
        return ResponseEntity.ok(addedPatient);
    }
    @PutMapping("/patients")
    public ResponseEntity<List<PatientDto>> GetallPatients(Authentication authentication) {
        String email = authentication.getName();
        Medecin medecin = medecinRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Medecin not found"));

        List<PatientDto> patients = medecinService.getAllPatients(medecin.getId());
        return ResponseEntity.ok(patients);
    }

    // ✅ Get One Patient
    @PutMapping("/patients/{patientId}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long patientId, Authentication authentication) {
        String email = authentication.getName();
        Medecin medecin = medecinRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Medecin not found"));

        PatientDto patient = medecinService.getPatientById(medecin.getId(), patientId);
        return ResponseEntity.ok(patient);
    }

}

