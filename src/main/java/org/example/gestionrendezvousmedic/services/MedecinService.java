package org.example.gestionrendezvousmedic.services;
//Create patient .getallpatiens
import jakarta.transaction.Transactional;
import org.example.gestionrendezvousmedic.Exception.AppointmentNotfoundNotadmin;
import org.example.gestionrendezvousmedic.Exception.PatientNotFoundNotadmin;
import org.example.gestionrendezvousmedic.Exception.Patientaddedalreadytomedecin;
import org.example.gestionrendezvousmedic.dtos.*;

import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.models.Patient;
import org.example.gestionrendezvousmedic.models.Rendezvous;
import org.example.gestionrendezvousmedic.models.Role;
import org.example.gestionrendezvousmedic.repos.MedecinRepository;
import org.example.gestionrendezvousmedic.repos.RendezVousRepository;
import org.example.gestionrendezvousmedic.repos.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.gestionrendezvousmedic.Exception.GlobalExceptionHandler;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedecinService {
private  final MedecinRepository medecinRepository;
    private final RendezVousRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private static final Logger logger = LoggerFactory.getLogger(MedecinService.class);
    public MedecinService(MedecinRepository medecinRepository, RendezVousRepository appointmentRepository, PatientRepository patientRepository) {
        this.medecinRepository = medecinRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public MedecinDashboardDto getDashboardData(Long medecinId) {
        int totalAppointments = appointmentRepository.countByMedecinId(medecinId);
        int totalPatients = patientRepository.countDistinctByMedecinId(medecinId);
      List<PatientsummaryDto> listofclients = patientRepository.findByMedecinId(medecinId).stream().map(patient -> new PatientsummaryDto(patient.getName(), patient.getEmail())).collect(Collectors.toList());
        List<AppointmentSummaryDto> recentAppointments = appointmentRepository
                .findTop5ByMedecinIdOrderByDateDesc(medecinId)
                .stream()
                .map(app -> new AppointmentSummaryDto(

                                app.getPatient().getName(),
                        app.getDate().atStartOfDay(),
                                app.getStatus()
                        ))
                .collect(Collectors.toList());

        return new MedecinDashboardDto(totalAppointments, totalPatients, recentAppointments,listofclients);
    }
    @Transactional
    public RendezVousDto getRendezVousData(Long medecinId,Long appointmentId) {
        logger.info("Looking for rendez-vous for medecinId: {} and manages appointmentID {} ", medecinId, appointmentId);
        Rendezvous rendezVous = appointmentRepository.findByIdAndMedecinId(medecinId,appointmentId).orElseThrow(()-> new AppointmentNotfoundNotadmin("Rendez vous non trouve ou non associe avec medecin"+medecinId));
        return new RendezVousDto(rendezVous.getId(),rendezVous.getMedecin().getId(),rendezVous.getPatient().getId(), rendezVous.getDate().atStartOfDay(),rendezVous.getStatus());
    }



    public List<RendezVousDto> getAllRendezVous(Long medecinId) {
        logger.info("Fetching all rendez-vous for medecinId: {}", medecinId);
        return appointmentRepository.findByMedecinId(medecinId)
                .stream()
                .map(r -> new RendezVousDto(r.getId(), r.getMedecin().getId(), r.getPatient().getId(),
                        r.getDate().atStartOfDay(), r.getStatus()))
                .collect(Collectors.toList());
    }
    @Transactional
    public RendezVousDto updateRendezVous(Long medecinId, Long rendezVousId, RendezVousDto dto) {
        logger.info("Updating rendez-vous {} for medecinId: {}", rendezVousId, medecinId);
        Rendezvous rendezVous = appointmentRepository.findByIdAndMedecinId(rendezVousId, medecinId)
                .orElseThrow(() -> new RuntimeException("Rendez vous non trouve ou non associe avec medecin"+medecinId));
        rendezVous.setDate(LocalDate.from(dto.getDate()));
        rendezVous.setStatus(dto.getStatus());
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        rendezVous.setPatient(patient);
        Rendezvous updated = appointmentRepository.save(rendezVous);
        return new RendezVousDto(updated.getId(), updated.getMedecin().getId(),
                updated.getPatient().getId(), updated.getDate().atStartOfDay(), updated.getStatus());
    }

    @Transactional
    public void deleteRendezVous(Long medecinId, Long rendezVousId) {
        logger.info("Deleting rendez-vous {} for medecinId: {}", rendezVousId, medecinId);
        Rendezvous rendezVous = appointmentRepository.findByIdAndMedecinId(rendezVousId, medecinId)
                .orElseThrow(() -> new RuntimeException("Rendez vous non trouve ou non associe avec medecin"+medecinId));
        appointmentRepository.delete(rendezVous);
    }

    // Patient CRUD
    @Transactional
    public AssignPatientDto addPatient(Long medecinId, String patientemail) {

        logger.info("Adding patient {} to medecin {}", patientemail, medecinId);

        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Medecin not found"));
        boolean alreadyAssigned = medecin.getPatients()
                .stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(patientemail));
        Patient patient = patientRepository.findByEmail(patientemail)
                .orElseThrow(() -> new PatientNotFoundNotadmin(" patient avec email: " + patientemail +" n'existe pas!"));
if (alreadyAssigned) {
    throw new Patientaddedalreadytomedecin("Patient " + patientemail + " already exists");
}


        patient.setMedecin(medecin);

        medecin.getPatients().add(patient);

        Patient saved = patientRepository.save(patient);

        return new AssignPatientDto(patientemail,medecinId);
    }


    public List<PatientDto> getAllPatients(Long medecinId) {
        logger.info("Fetching all patients for medecinId: {}", medecinId);
        return patientRepository.findByMedecinId(medecinId)
                .stream()
                .map(p -> new PatientDto(p.getName() ,p.getId(), p.getEmail()))
                .collect(Collectors.toList());
    }

    public PatientDto getPatient(Long medecinId, Long patientId) {
        logger.info("Fetching patient {} for medecinId: {}", patientId, medecinId);
        Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId)
                .orElseThrow(() -> new RuntimeException("Patient not found or not associated with medecin"));
        return new PatientDto(patient.getName() ,patient.getId(), patient.getEmail());
    }

    @Transactional
    public PatientDto updatePatient(Long medecinId, Long patientId, PatientDto dto) {
        logger.info("Updating patient {} for medecinId: {}", patientId, medecinId);
        Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId)
                .orElseThrow(() -> new RuntimeException("Patient not found or not associated with medecin"));
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());

        Patient updated = patientRepository.save(patient);
        return new PatientDto(updated.getName(), updated.getId(), updated.getEmail());
    }

    @Transactional
    public PatientDto deletePatient(Long medecinId, Long patientId) {
        logger.info("Deleting patient {} for medecinId: {}", patientId, medecinId);
        PatientDto deledpatient = getPatient(medecinId, patientId);
        Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId)
                .orElseThrow(() -> new PatientNotFoundNotadmin("Patient non trouve ou non associé avec vous"));
        patientRepository.delete(patient);
        return deledpatient;
    }
    public PatientDto getPatientDto(Long medecinId, Long patientId) {
        logger.info("Found Patient:{} for medecinId: {}", patientId, medecinId);
      Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId).orElseThrow(() -> new PatientNotFoundNotadmin("Patient non trouve ou non associé avec vous"));
      return new PatientDto(patient.getName() ,patient.getId(), patient.getEmail());
    }
}