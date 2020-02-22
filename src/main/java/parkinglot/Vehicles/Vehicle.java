package parkinglot.Vehicles;

import parkinglot.Enums.VehicleType;

public abstract class Vehicle {

    private final VehicleType vehicleType;

    private String regNumber;

    private String colour;

    public Vehicle(VehicleType vehicleType, String regNumber, String colour) {
        this.vehicleType = vehicleType;
        this.regNumber = regNumber;
        this.colour = colour;
    }

    public String getRegNumber() {
        return this.regNumber;
    }

    public String getColour() {
        return this.colour;
    }
}
