package parkinglot.ParkingSpot;

import parkinglot.Enums.ParkingSpotType;
import parkinglot.Vehicles.Vehicle;

public abstract class ParkingSpot {

    private final ParkingSpotType type;

    private String number;

    private Vehicle vehicle;

    private Boolean isFree;

    public ParkingSpot(ParkingSpotType type, String number) {
        this.type = type;
        this.number = number;
        this.isFree = true;
    }

    public String getNumber() {
        return this.number;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
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
