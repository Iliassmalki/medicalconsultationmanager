package org.example.gestionrendezvousmedic.services;
//Create patient .getallpatiens
import jakarta.transaction.Transactional;
import org.example.gestionrendezvousmedic.Exception.PatientNotFoundNotadmin;
import org.example.gestionrendezvousmedic.dtos.MedecinDashboardDto;
import org.example.gestionrendezvousmedic.dtos.AppointmentSummaryDto;
import org.example.gestionrendezvousmedic.dtos.PatientDto;
import org.example.gestionrendezvousmedic.dtos.RendezVousDto;
import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.models.Patient;
import org.example.gestionrendezvousmedic.models.Rendezvous;
import org.example.gestionrendezvousmedic.models.Role;
import org.example.gestionrendezvousmedic.repos.MedecinRepository;
import org.example.gestionrendezvousmedic.repos.RendezVousRepository;
import org.example.gestionrendezvousmedic.repos.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.gestionrendezvousmedic.Exception.GlobalExceptionHandler;
import java.time.LocalDate;
import java.util.List;
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

        List<AppointmentSummaryDto> recentAppointments = appointmentRepository
                .findTop5ByMedecinIdOrderByDateDesc(medecinId)
                .stream()
                .map(app -> new AppointmentSummaryDto(

                                app.getPatient().getName(),
                        app.getDate().atStartOfDay(),
                                app.getStatus()
                        ))
                .collect(Collectors.toList());

        return new MedecinDashboardDto(totalAppointments, totalPatients, recentAppointments);
    }
    @Transactional
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
                .orElseThrow(() -> new RuntimeException("Rendez-vous not found or not authorized"));
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
                .orElseThrow(() -> new RuntimeException("Rendez-vous not found or not authorized"));
        appointmentRepository.delete(rendezVous);
    }

    // Patient CRUD
    @Transactional
    public PatientDto createPatient(Long medecinId, PatientDto dto) {
        logger.info("Creating patient for medecinId: {}", medecinId);
        if (!medecinRepository.existsById(medecinId)) {
            throw new RuntimeException("Medecin not found");
        }
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());

        patient.setRole(Role.PATIENT);
        Patient saved = patientRepository.save(patient);
        return new PatientDto(saved.getName(), saved.getId(),saved.getEmail());
    }

    public List<PatientDto> getAllPatients(Long medecinId) {
        logger.info("Fetching all patients for medecinId: {}", medecinId);
        return patientRepository.findByMedecinId(medecinId)
                .stream()
                .map(p -> new PatientDto(p.getName(),p.getId() , p.getEmail()))
                .collect(Collectors.toList());
    }

    public PatientDto getPatient(Long medecinId, Long patientId) {
        logger.info("Fetching patient {} for medecinId: {}", patientId, medecinId);
        Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId)
                .orElseThrow(() -> new RuntimeException("Patient not found or not associated with medecin"));
        return new PatientDto(patient.getName(),patient.getId() , patient.getEmail());
    }

    @Transactional
    public PatientDto updatePatient(Long medecinId, Long patientId, PatientDto dto) {
        logger.info("Updating patient {} for medecinId: {}", patientId, medecinId);
        Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId)
                .orElseThrow(() -> new RuntimeException("Patient not found or not associated with medecin"));
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());

        Patient updated = patientRepository.save(patient);
        return new PatientDto(updated.getName(),updated.getId() , updated.getEmail());
    }

    @Transactional
    public void deletePatient(Long medecinId, Long patientId) {
        logger.info("Deleting patient {} for medecinId: {}", patientId, medecinId);
        Patient patient = patientRepository.findByIdAndMedecinId(patientId, medecinId)
                .orElseThrow(() -> new PatientNotFoundNotadmin("Patient non trouve ou non associé avec vous"));
        patientRepository.delete(patient);
    }
}
