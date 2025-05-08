package org.example.gestionrendezvousmedic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class PatientDto {
    String name;
    Long Id;
    String email;
}
