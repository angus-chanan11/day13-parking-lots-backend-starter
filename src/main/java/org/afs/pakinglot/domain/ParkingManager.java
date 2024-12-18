package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.afs.pakinglot.domain.strategies.AvailableRateStrategy;
import org.afs.pakinglot.domain.strategies.MaxAvailableStrategy;
import org.afs.pakinglot.domain.strategies.SequentiallyStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingManager {
    private final List<ParkingLot> parkingLots;
    private final ParkingBoy standardParkingBoy;
    private final String STANDARD_BOY = "Standard";
    private final ParkingBoy smartParkingBoy;
    private final String SMART_BOY = "Smart";
    private final ParkingBoy superSmartParkingBoy;
    private final String SUPER_SMART_BOY = "Super Smart";

    public ParkingManager() {
        parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1, "The Plaza Park", 9));
        parkingLots.add(new ParkingLot(2, "City Mall Garage", 12));
        parkingLots.add(new ParkingLot(3, "Office Tower Parking", 9));

        standardParkingBoy = new ParkingBoy(parkingLots, new SequentiallyStrategy());
        smartParkingBoy = new ParkingBoy(parkingLots, new MaxAvailableStrategy());
        superSmartParkingBoy =  new ParkingBoy(parkingLots, new AvailableRateStrategy());
    }

    public Ticket park(String plateNumber, String strategy) {
        if (!isPlateNumberValid(plateNumber)) {
            throw new IllegalArgumentException("Invalid plate number");
        }
        ParkingBoy parkingBoy = getParkingBoy(strategy);
        return parkingBoy.park(new Car(plateNumber));
    }

    public Car fetch(String plateNumber) {
        if (!isPlateNumberValid(plateNumber)) {
            throw new IllegalArgumentException("Invalid plate number");
        }

        Ticket ticketToFetch = parkingLots.stream()
                .flatMap(parkingLot -> parkingLot.getTickets().stream()
                        .filter(ticket -> ticket.plateNumber().equals(plateNumber)))
                        .findFirst()
                        .orElseThrow(UnrecognizedTicketException::new);

        return standardParkingBoy.fetch(ticketToFetch);
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void clearAllParkingLots() {
        parkingLots.forEach(ParkingLot::removeAllTickets);
    }

    private ParkingBoy getParkingBoy(String strategy) {
        return switch (strategy) {
            case STANDARD_BOY -> standardParkingBoy;
            case SMART_BOY -> smartParkingBoy;
            case SUPER_SMART_BOY -> superSmartParkingBoy;
            default -> throw new IllegalArgumentException("Invalid parking strategy");
        };
    }

    // check if plateNumber start with 2 letters and followed by "-" and 4 digits
    private boolean isPlateNumberValid(String plateNumber) {
        return plateNumber.matches("^[A-Z]{2}-\\d{4}$");
    }
}