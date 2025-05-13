package org.example.gestionrendezvousmedic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gestionrendezvousmedic.models.Medecin;

import java.util.List;
@Data
@AllArgsConstructor
public class PatientDashboardDto {
    String name;
    String email;
    String medecinName;

    int nr;
    List<RendezVousDto> rendezVousDtos;
}
