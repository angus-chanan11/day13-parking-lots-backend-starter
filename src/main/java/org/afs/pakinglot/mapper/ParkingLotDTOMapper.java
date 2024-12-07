package org.afs.pakinglot.mapper;

import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.model.ParkingLotDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingLotDTOMapper {

    public List<ParkingLotDTO> toParkingLotDTOs(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .map(parkingLot -> new ParkingLotDTO(
                        parkingLot.getId(),
                        parkingLot.getName(),
                        parkingLot.getCapacity(),
                        parkingLot.getTickets()))
                .collect(Collectors.toList());
    }
}
