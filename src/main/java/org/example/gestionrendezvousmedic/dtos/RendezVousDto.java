package org.example.gestionrendezvousmedic.dtos;



import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gestionrendezvousmedic.models.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class RendezVousDto {

    private Long id; // ID is usually only returned, not required on creation

    @NotNull(message = "Medecin ID is required")
    private Long medecinId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;
private String reason;
    @NotNull(message = "Date is required")
    @Future(message = "Date must be in the future")
    private LocalDateTime date;

    @NotNull(message = "Status is required")
    private Status status;

    public RendezVousDto() {}

   }

