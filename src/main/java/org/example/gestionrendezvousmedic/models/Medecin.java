package org.example.gestionrendezvousmedic.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data

public class Medecin extends User {

    private String specialite;

    public Medecin(String specialite) {
        this.specialite = specialite;
    }

    public Medecin(String name, String email, String specialite) {
        super(name, email, Role.MEDECIN);
        this.specialite = specialite;
    }

    public Medecin() {
        super(Role.MEDECIN);
    }
}
