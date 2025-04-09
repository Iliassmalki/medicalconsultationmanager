package org.example.gestionrendezvousmedic.repos;

import org.example.gestionrendezvousmedic.models.Medecin;
import org.example.gestionrendezvousmedic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<User> findByEmail(String email);
}
