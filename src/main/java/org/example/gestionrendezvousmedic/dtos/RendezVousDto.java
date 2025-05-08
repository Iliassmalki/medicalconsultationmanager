package org.example.gestionrendezvousmedic.dtos;

import lombok.Data;

import java.time.LocalDateTime;
 @Data
public class RendezVousDto {
    private Long id;
    private Long medecinId;
    private Long patientId;
    private LocalDateTime date;
    private String status;

    public RendezVousDto() {}

    public RendezVousDto(Long id, Long medecinId, Long patientId, LocalDateTime date, String status) {
        this.id = id;
        this.medecinId = medecinId;
        this.patientId = patientId;
        this.date = date;
        this.status = status;
    }


}