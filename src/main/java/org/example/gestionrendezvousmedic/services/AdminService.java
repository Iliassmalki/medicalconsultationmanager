package org.example.gestionrendezvousmedic.services;

import jakarta.transaction.Transactional;
import org.example.gestionrendezvousmedic.dtos.AdminDashboardDto;

import org.example.gestionrendezvousmedic.dtos.PatientDto;
import org.example.gestionrendezvousmedic.dtos.RendezVousDto;
import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.models.Patient;
import org.example.gestionrendezvousmedic.models.Rendezvous;
import org.example.gestionrendezvousmedic.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepository;
    private final MedecinRepository medecinRepository;
    private final AdminRepository adminRepository;
    private final PatientRepository patientRepository;
    private final RendezVousRepository appointmentRepository;

    @Autowired
    public AdminService(UserRepository userRepository,
                        MedecinRepository medecinRepository,
                        AdminRepository adminRepository,
                        PatientRepository patientRepository,
                        RendezVousRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.medecinRepository = medecinRepository;
        this.adminRepository = adminRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AdminDashboardDto getDashboardData() {
        int medecins = (int) medecinRepository.count();
        int appointments = (int) appointmentRepository.count();
        int patients = (int) patientRepository.count();
        int users = (int) userRepository.count();
        List<Medecin> medecinList = medecinRepository.showAllMedecins().orElseThrow(()-> new RuntimeException("Aucun medecin existe."));
        List <Patient> patientList = patientRepository.showAllPatients().orElseThrow(()-> new RuntimeException("Aucun Patient n'existe."));
        return new AdminDashboardDto(users, appointments, medecins, patients);
    }

    // --- Example CRUD methods below ---

    // Patients

    // Medecins


    public void deleteMedecin(Long id) {
        medecinRepository.deleteById(id);
    }

    // Rendezvous
  /*  @Transactional
    public RendezVousDto createRendezVous(Long medecinId, RendezVousDto dto) {
        logger.info("Creating rendez-vous for medecinId: {}", medecinId);
        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Medecin not found"));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Rendezvous rendezVous = new Rendezvous();
        rendezVous.setMedecin(medecin);
        rendezVous.setPatient(patient);
        rendezVous.setDate(LocalDate.from(dto.getDate()));
        rendezVous.setStatus(dto.getStatus());
        Rendezvous saved = appointmentRepository.save(rendezVous);

        return new RendezVousDto(saved.getId(), saved.getMedecin().getId(), saved.getPatient().getId(),
                saved.getDate().atStartOfDay(), saved.getStatus());
    }

    public void deleteRendezVous(Long id) {
        appointmentRepository.deleteById(id);
    }*/
}
