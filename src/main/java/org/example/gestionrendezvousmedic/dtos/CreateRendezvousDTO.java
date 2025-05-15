package org.example.gestionrendezvousmedic.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data

public class CreateRendezvousDTO {
        @NotBlank(message = "Patient email is required")
        @Email(message = "Invalid patient email format")
        private String patientEmail;

        @NotBlank(message = "Medecin email is required")
        @Email(message = "Invalid medecin email format")
        private String medecinEmail;

        @NotNull(message = "Date is required")
        @Future(message = "Date must be in the future")
        private LocalDateTime date;

        private String reason;
    }


