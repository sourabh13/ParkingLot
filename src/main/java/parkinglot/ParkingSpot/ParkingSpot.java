package parkinglot.ParkingSpot;

import parkinglot.Enums.ParkingSpotType;
import parkinglot.Vehicles.Vehicle;

public abstract class ParkingSpot {

    private final ParkingSpotType type;

    private Integer number;

    private Vehicle vehicle;

    private Boolean isFree;

    public ParkingSpot(ParkingSpotType type, Integer number) {
        this.type = type;
        this.number = number;
        this.isFree = true;
    }

    public Integer getNumber() {
        return this.number;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public ParkingSpotType getType() {
        return this.type;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isFree = false;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isFree = true;
    }
}
