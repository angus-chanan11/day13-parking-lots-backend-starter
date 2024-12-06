package org.afs.pakinglot.controller;

import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.model.PlateNumber;
import org.afs.pakinglot.model.PlateNumberAndParkingBoyType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-manager")
public class ParkingManagerController {

    private final ParkingManager parkingManager;

    public ParkingManagerController(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    @GetMapping("/parking-lots")
    public ResponseEntity<List<ParkingLot>> getParkingLots() {
        return ResponseEntity.ok(parkingManager.getParkingLots());
    }

    @PostMapping("/park")
    public ResponseEntity<Ticket> park(@RequestBody PlateNumberAndParkingBoyType plateNumberAndParkingBoyType) {
        String plateNumber = plateNumberAndParkingBoyType.getPlateNumber();
        String parkingType = plateNumberAndParkingBoyType.getParkingBoyType();
        Ticket ticket = parkingManager.park(plateNumber, parkingType);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<ParkingLot>> fetch(@RequestBody PlateNumber plateNumber) {
        List<ParkingLot> parkingLots = parkingManager.fetch(plateNumber.getPlateNumber());
        return ResponseEntity.ok(parkingLots);
    }
}