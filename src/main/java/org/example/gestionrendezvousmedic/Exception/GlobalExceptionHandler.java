package org.example.gestionrendezvousmedic.Exception;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("error email already in use", ex.getMessage())
        );
    }

  @ExceptionHandler(UserdoesntExistException.class)
    public ResponseEntity<?> handleUserdoesntExist(UserdoesntExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error user doesnt exist", ex.getMessage())
        );
  }
  @ExceptionHandler(PatientNotFoundNotadmin.class)
    public ResponseEntity<?> handlePatientNotFoundNotadmin(PatientNotFoundNotadmin ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error patient not found", ex.getMessage())
        );
  }
}
