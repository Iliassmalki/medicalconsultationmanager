package org.example.gestionrendezvousmedic.dtos;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class AppointmentSummaryDto {
    private String patientName;
    private LocalDateTime appointmentDate;
    private String status;

    public AppointmentSummaryDto() {}

    public AppointmentSummaryDto(String patientName, LocalDateTime appointmentDate, String status) {
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }




    // Getters and Setters
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
