package org.example.gestionrendezvousmedic.Controller;

import org.example.gestionrendezvousmedic.dtos.LoginUserDto;
import org.example.gestionrendezvousmedic.dtos.RegisterUserDto;
import org.example.gestionrendezvousmedic.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@RequestBody RegisterUserDto dto) {
        return ResponseEntity.ok(authService.signupPatient(dto));
    }

    @PostMapping("/signup/medecin")
    public ResponseEntity<?> registerMedecin(@RequestBody RegisterUserDto dto) {
        return ResponseEntity.ok(authService.signupMedecin(dto));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterUserDto dto) {
        return ResponseEntity.ok(authService.signupAdmin(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto dto) {


         switch (dto.getRole()) {
         case ADMIN:
           return ResponseEntity.ok(authService.authenticateAdmin(dto));
          case MEDECIN:
          return ResponseEntity.ok(authService.authenticateMedecin(dto));
         case PATIENT:
        return ResponseEntity.ok(authService.authenticatePatient(dto));
           default:
         return ResponseEntity.badRequest().body("Invalid role specified.");
         }
         }}
