package org.example.gestionrendezvousmedic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.nio.file.Path;
import java.time.LocalDate;
@Data
@Entity
public class Rendezvous {
    @Id
    Long id;
    LocalDate date;
    String status;

    @ManyToOne
   @JoinColumn(name = "patient_id")  // foreign key column in the rendezvous table
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "medecin_id")  // foreign key column in the rendezvous table
    private Medecin medecin;

}
