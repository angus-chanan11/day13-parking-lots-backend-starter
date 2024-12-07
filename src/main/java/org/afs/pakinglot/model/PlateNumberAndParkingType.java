package org.afs.pakinglot.model;

public class PlateNumberAndParkingType {
    private String plateNumber;
    private String parkingType;

    public PlateNumberAndParkingType(String plateNumber, String parkingType) {
        this.plateNumber = plateNumber;
        this.parkingType = parkingType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getParkingType() {
        return parkingType;
    }
}
