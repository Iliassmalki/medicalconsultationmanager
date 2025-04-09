package org.example.gestionrendezvousmedic.repos;

import org.example.gestionrendezvousmedic.models.Rendezvous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RendezVousRepository extends JpaRepository<Rendezvous, Long> {
    Optional<Rendezvous> findById(String Id);
}