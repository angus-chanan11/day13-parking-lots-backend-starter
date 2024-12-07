package org.afs.pakinglot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.afs.pakinglot.domain.CarPlateGenerator;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.model.PlateNumber;
import org.afs.pakinglot.model.PlateNumberAndParkingBoyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class ParkingManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingManager parkingManager;

    @Autowired
    private ObjectMapper objectMapper;

    private List<ParkingLot> parkingLots;

    @BeforeEach
    void setUp() {
        parkingLots = List.of(
                new ParkingLot(1, "The Plaza Park", 9),
                new ParkingLot(2, "City Mall Garage", 12),
                new ParkingLot(3, "Office Tower Parking", 9)
        );
    }

    @Test
    void should_return_parking_lots_when_getParkingLot() throws Exception {

        mockMvc.perform(get("/api/v1/parking-manager/parking-lots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @ParameterizedTest
    @CsvSource({
            "Standard",
            "Smart",
            "Super Smart"
    })
    void should_return_ticket_when_park_given_valid_parking(String parkingType) throws Exception {
        String plateNumber = CarPlateGenerator.generatePlate();

        mockMvc.perform(post("/api/v1/parking-manager/park")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PlateNumberAndParkingBoyType(plateNumber, parkingType))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plateNumber").isNotEmpty())
                .andExpect(jsonPath("$.parkingLot").isNumber())
                .andExpect(jsonPath("$.position").isNumber());
    }

    @Test
    void should_throw_exception_when_park_given_invalid_parking() throws Exception {
        String plateNumber = CarPlateGenerator.generatePlate();
        String invalidParkingType = "INVALID";

        mockMvc.perform(post("/api/v1/parking-manager/park")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PlateNumberAndParkingBoyType(plateNumber, invalidParkingType))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid parking strategy"));
    }

    @Test
    void should_return_parking_lots_when_fetch_given_plate_number() throws Exception {
        String plateNumber = CarPlateGenerator.generatePlate();
        parkingManager.park(plateNumber, "Standard");

        mockMvc.perform(post("/api/v1/parking-manager/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PlateNumber(plateNumber))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void should_throw_exception_when_fetch_given_not_existing_plate_number() throws Exception {
        String invalidPlateNumber = CarPlateGenerator.generatePlate();

        mockMvc.perform(post("/api/v1/parking-manager/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("plateNumber", invalidPlateNumber))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Car not found"));
    }
}