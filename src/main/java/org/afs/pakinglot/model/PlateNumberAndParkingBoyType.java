package org.afs.pakinglot.model;

public class PlateNumberAndParkingBoyType {
    private String plateNumber;
    private String parkingBoyType;

    public PlateNumberAndParkingBoyType(String plateNumber, String parkingBoyType) {
        this.plateNumber = plateNumber;
        this.parkingBoyType = parkingBoyType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getParkingBoyType() {
        return parkingBoyType;
    }
}
