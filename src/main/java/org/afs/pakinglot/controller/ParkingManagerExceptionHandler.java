package org.afs.pakinglot.controller;

import org.afs.pakinglot.domain.exception.NoAvailablePositionException;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParkingManagerExceptionHandler {

    // Return 400 Bad Request when IllegalArgumentException is thrown
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Return 404 Not Found when UnrecognizedTicketException is thrown
    @ExceptionHandler(UnrecognizedTicketException.class)
    public ResponseEntity<String> handleUnrecognizedTicketException(UnrecognizedTicketException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
    }

    // Return 503 Service Unavailable NoAvailablePositionException is thrown
    @ExceptionHandler(NoAvailablePositionException.class)
    public ResponseEntity<String> handleNoAvailablePositionException(NoAvailablePositionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("No available position");
    }
}
