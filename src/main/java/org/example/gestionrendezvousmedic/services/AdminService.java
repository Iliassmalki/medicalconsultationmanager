package org.example.gestionrendezvousmedic.services;

import org.example.gestionrendezvousmedic.dtos.AdminDashboardDto;
import org.example.gestionrendezvousmedic.repos.*;

public class AdminService {
    private UserRepository userRepository;
    private MedecinRepository medecinRepository;
    private AdminRepository adminRepository;
    private PatientRepository patientRepository;
    private RendezVousRepository appointmentRepository;
    public AdminDashboardDto getDashboardData() {
        int medecins = (int) medecinRepository.count();
        int appointments = (int) appointmentRepository.count();
        int patients = (int) patientRepository.count();
        int users = (int) userRepository.count();

        return new AdminDashboardDto(users, appointments, medecins,patients);
    }

}
