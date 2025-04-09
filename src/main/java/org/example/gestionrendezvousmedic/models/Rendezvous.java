package org.example.gestionrendezvousmedic.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
@Entity
public class Rendezvous {
    @Id
    Long id;
    LocalDate date;
    String status;
   // @ManyToOne
   // @JoinColumn(name = "patient_id")  // foreign key column in the rendezvous table
    //private Patient patient;
}
