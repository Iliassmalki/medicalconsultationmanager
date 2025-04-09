package org.example.gestionrendezvousmedic.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;
@Entity
@Data


public class Patient extends User {
  //  @OneToMany(mappedBy = "patient")
//List<Rendezvous> listRendezvous;

    public Patient(List<Rendezvous> listRendezvous) {
       // this.listRendezvous = listRendezvous;
    }

    public Patient(String name, String email, List<Rendezvous> listRendezvous) {
        super(name, email, Role.PATIENT);
        //this.listRendezvous = listRendezvous;
    }

    public Patient() {
super(Role.PATIENT);
    }
}
