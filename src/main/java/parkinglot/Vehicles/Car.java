package parkinglot.Vehicles;

import parkinglot.Enums.VehicleType;

public class Car extends Vehicle{

    public Car(String regNumber, String colour) {
        super(VehicleType.CAR, regNumber, colour);
    }
}
