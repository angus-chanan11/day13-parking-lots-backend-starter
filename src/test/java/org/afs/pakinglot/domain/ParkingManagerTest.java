package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.exception.NoAvailablePositionException;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingManagerTest {

    private ParkingManager parkingManager;

    @BeforeEach
    void setUp() {
        parkingManager = new ParkingManager();
    }

    @ParameterizedTest
    @CsvSource({
            "Standard, The Plaza Park",
            "Smart, City Mall Garage",
            "Super Smart, The Plaza Park"
    })
    void should_return_ticket_when_park_given_standard_parking(String strategy, String expectedParkingLotName) {
        // given
        String plateNumber = CarPlateGenerator.generatePlate();

        // when
        Ticket ticket = parkingManager.park(plateNumber, strategy);

        // then
        assertNotNull(ticket);
        assertEquals(plateNumber, ticket.plateNumber());
        assertTrue(parkingManager.getParkingLots().stream()
                .anyMatch(parkingLot -> parkingLot.getName().equals(expectedParkingLotName) && parkingLot.contains(ticket)));
    }

    @Test
    void should_throw_exception_when_park_given_invalid_parking() {
        // given
        String plateNumber = CarPlateGenerator.generatePlate();
        String invalidStrategy = "INVALID";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> parkingManager.park(plateNumber, invalidStrategy));
    }

    @Test
    void should_throw_exception_when_park_given_invalid_plate_number() {
        // given
        String plateNumber = "INVALID";
        String invalidStrategy = "Standard";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> parkingManager.park(plateNumber, invalidStrategy));
    }

    @ParameterizedTest
    @CsvSource({
            "Standard",
            "Smart",
            "Super Smart"
    })
    void should_throw_exception_when_park_given_full_lots(String strategy) {
        // given
        int totalParkingSpace = parkingManager.getParkingLots().stream().mapToInt(ParkingLot::getCapacity).sum();
        for (int iteration = 0; iteration < totalParkingSpace; iteration++) {
            parkingManager.park(CarPlateGenerator.generatePlate(), strategy);
        }
        String plateNumber = CarPlateGenerator.generatePlate();

        // when & then
        assertThrows(NoAvailablePositionException.class, () -> parkingManager.park(plateNumber, strategy));
    }

    @Test
    void should_return_parking_lots_when_getParkingLot() {
        // given

        // when
        List<ParkingLot> parkingLots = parkingManager.getParkingLots();

        // then
        assertNotNull(parkingLots);
        assertEquals(3, parkingLots.size());
    }

    @Test
    void should_return_car_when_fetch_given_plate_number() {
        // given
        String plateNumber = CarPlateGenerator.generatePlate();
        Ticket ticket = parkingManager.park(plateNumber, "Standard");

        // when
        Car car = parkingManager.fetch(plateNumber);

        // then
        assertNotNull(car);
        assertEquals(plateNumber, car.plateNumber());
        assertFalse(parkingManager.getParkingLots().stream().anyMatch(parkingLot -> parkingLot.contains(ticket)));
    }

    @Test
    void should_throw_exception_when_fetch_given_not_existing_plate_number() {
        // given
        String invalidPlateNumber = CarPlateGenerator.generatePlate();

        // when & then
        assertThrows(UnrecognizedTicketException.class, () -> parkingManager.fetch(invalidPlateNumber));
    }

    @Test
    void should_throw_exception_when_fetch_given_invalid_plate_number() {
        // given
        String plateNumber = "INVALID";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> parkingManager.fetch(plateNumber));
    }
}