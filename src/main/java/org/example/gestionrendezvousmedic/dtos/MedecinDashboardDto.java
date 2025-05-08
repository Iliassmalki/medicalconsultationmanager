package org.example.gestionrendezvousmedic.dtos;

import org.example.gestionrendezvousmedic.dtos.AppointmentSummaryDto;

import java.util.List;

public class MedecinDashboardDto {

    private int totalAppointments;
    private int totalPatients;
    private List<AppointmentSummaryDto> recentAppointments;

    // Constructors
    public MedecinDashboardDto() {}

    public MedecinDashboardDto(int totalAppointments, int totalPatients, List<AppointmentSummaryDto> recentAppointments) {
        this.totalAppointments = totalAppointments;
        this.totalPatients = totalPatients;
        this.recentAppointments = recentAppointments;
    }

    // Getters and Setters
    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public int getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(int totalPatients) {
        this.totalPatients = totalPatients;
    }

    public List<AppointmentSummaryDto> getRecentAppointments() {
        return recentAppointments;
    }

    public void setRecentAppointments(List<AppointmentSummaryDto> recentAppointments) {
        this.recentAppointments = recentAppointments;
    }
}
