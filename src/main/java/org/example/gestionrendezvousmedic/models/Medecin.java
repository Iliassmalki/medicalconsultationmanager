package org.example.gestionrendezvousmedic.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Medecin")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medecin extends User {

    @Column(name = "specialite")
    private String specialite;

    public Medecin(String name, String email, String password, Role role, String specialite) {
        super(name, email, role);
        this.setPassword(password); // set password manually
        this.specialite = specialite;
    }
}
