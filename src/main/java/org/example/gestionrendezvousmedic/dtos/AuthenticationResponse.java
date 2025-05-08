package org.example.gestionrendezvousmedic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class AuthenticationResponse {

        private String token;

        public AuthenticationResponse() {}

        public AuthenticationResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;

    }}