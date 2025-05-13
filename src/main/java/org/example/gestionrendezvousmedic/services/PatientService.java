package org.example.gestionrendezvousmedic.services;

import org.example.gestionrendezvousmedic.Exception.AppointmentNotfoundNotadmin;
import org.example.gestionrendezvousmedic.Exception.Medecinnotfound;
import org.example.gestionrendezvousmedic.Exception.PatientNotFoundNotadmin;
import org.example.gestionrendezvousmedic.dtos.PatientDashboardDto;
import org.example.gestionrendezvousmedic.dtos.PatientDto;
import org.example.gestionrendezvousmedic.dtos.RendezVousDto;
import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.models.Patient;
import org.example.gestionrendezvousmedic.models.Rendezvous;
import org.example.gestionrendezvousmedic.models.Status;
import org.example.gestionrendezvousmedic.repos.MedecinRepository;
import org.example.gestionrendezvousmedic.repos.PatientRepository;
import org.example.gestionrendezvousmedic.repos.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Service
public class PatientService {
    @Autowired
    private final MedecinRepository medecinRepository;
    private final PatientRepository patientRepository;
private final RendezVousRepository rendezVousRepository;
    public PatientService(MedecinRepository medecinRepository, PatientRepository patientRepository, RendezVousRepository rendezVousRepository) {
        this.medecinRepository = medecinRepository;
        this.patientRepository = patientRepository;
        this.rendezVousRepository = rendezVousRepository;
    }
public PatientDashboardDto dashboard( Long patientId) {
    Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new PatientNotFoundNotadmin("Patient non disponible"));

    Medecin medecin = patient.getMedecin();
    if (medecin == null) {
        throw new Medecinnotfound("Aucun médecin assigné à ce patient.");
    }

   int nr= rendezVousRepository.countByMedecinId(medecin.getId());
    List <RendezVousDto> rendezVousDtoList = patient.getListRendezvous().stream().map( rdv -> new RendezVousDto( rdv.getId(),
            rdv.getMedecin().getId(),
            rdv.getPatient().getId(),
            rdv.getDate(),
            rdv.getStatus())).toList();

    return new PatientDashboardDto(patient.getName(),medecin.getName(),patient.getEmail(),nr,rendezVousDtoList);
}
    public RendezVousDto CreatRendezVousDto(RendezVousDto input, Long patientId) {
        Rendezvous rd = new Rendezvous();

        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new PatientNotFoundNotadmin("Patient non disponible"));
        LocalDate newAppointmentDate = rd.getDate().toLocalDate();
        boolean alreadyassignedrdv =  patient.getListRendezvous().stream().anyMatch(rdv->rdv.getDate().equals(newAppointmentDate));
        if (alreadyassignedrdv) {
            throw new RuntimeException("Rendez-vous dans cette date deja assigne avec vous,choisissez un autre jour.");
        }

        rd.setDate(input.getDate());
        rd.setStatus(Status.PENDING);
        Medecin medecin = medecinRepository.findById(input.getMedecinId()).orElseThrow(() -> new Medecinnotfound("Medecin non disponible ou non associe avec vous."));


    rd.setStatus(Status.PENDING);
    rd.setMedecin(medecin);
    return new RendezVousDto(rd.getId(),input.getMedecinId(),patientId,rd.getDate(),rd.getStatus());

}
public  void DeleteRendezVous(RendezVousDto input,Long patientId) {
    Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new PatientNotFoundNotadmin("Patient non disponible"));
    Rendezvous rdv = rendezVousRepository.findByPatientId(patientId).orElseThrow(()-> new AppointmentNotfoundNotadmin("Rendez non trouve ou non assigne avec vous!"));
    rendezVousRepository.delete(rdv);
}
}