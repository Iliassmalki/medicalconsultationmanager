package org.example.gestionrendezvousmedic.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestionrendezvousmedic.models.Medecin;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long id; // Optional for updates, ignored for creation

    @NotBlank(message = "Patient ID is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotNull(message = "Patient ID is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotBlank(message = "medecin ID est important")

    @NotBlank(message = "password  is required")
    @Size(max = 10, message = "password must be less than 100 characters")
    private String password;

    public PatientDto(String name, Long id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

}
