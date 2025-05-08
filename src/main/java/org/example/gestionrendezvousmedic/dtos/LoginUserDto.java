package org.example.gestionrendezvousmedic.dtos;

import lombok.Data;
import org.example.gestionrendezvousmedic.models.Role;

@Data
public class LoginUserDto {
    private String email;

    private String password;
    private Role role;


}
