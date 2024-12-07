package org.afs.pakinglot.controller;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.mapper.ParkingLotDTOMapper;
import org.afs.pakinglot.model.ParkingLotDTO;
import org.afs.pakinglot.model.PlateNumber;
import org.afs.pakinglot.model.PlateNumberAndParkingType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-manager")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingManagerController {

    private final ParkingManager parkingManager;

    private final ParkingLotDTOMapper parkingLotDTOMapper;

    public ParkingManagerController(ParkingManager parkingManager, ParkingLotDTOMapper parkingLotDTOMapper) {
        this.parkingManager = parkingManager;
        this.parkingLotDTOMapper = parkingLotDTOMapper;
    }

    @GetMapping("/parking-lots")
    public ResponseEntity<List<ParkingLotDTO>> getParkingLots() {
        List<ParkingLot> parkingLots = parkingManager.getParkingLots();
        return ResponseEntity.ok(parkingLotDTOMapper.toParkingLotDTOs(parkingLots));
    }

    @PostMapping("/park")
    public ResponseEntity<Ticket> park(@RequestBody PlateNumberAndParkingType plateNumberAndParkingType) {
        String plateNumber = plateNumberAndParkingType.getPlateNumber();
        String parkingType = plateNumberAndParkingType.getParkingType();
        Ticket ticket = parkingManager.park(plateNumber, parkingType);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/fetch")
    public ResponseEntity<Car> fetch(@RequestBody PlateNumber plateNumber) {
        Car car = parkingManager.fetch(plateNumber.getPlateNumber());
        return ResponseEntity.ok(car);
    }
}