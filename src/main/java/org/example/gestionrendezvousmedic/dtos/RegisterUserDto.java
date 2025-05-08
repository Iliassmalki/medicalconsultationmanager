package org.example.gestionrendezvousmedic.dtos;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
    private String specialite;
}
