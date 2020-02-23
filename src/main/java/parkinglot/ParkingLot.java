package parkinglot;

import java.util.ArrayList;
import java.util.List;

import parkinglot.ParkingSpot.ParkingSpot;
import parkinglot.Vehicles.Vehicle;

public class ParkingLot {

    private static ParkingLot parkingLot = null;

    private Integer numberOfFloors;

    private List<ParkingFloor> parkingFloors;

    private ParkingLot() {

        this.numberOfFloors = 0;
        parkingFloors = new ArrayList<ParkingFloor>();
    }

    public static ParkingLot getInstance() {

        if(parkingLot == null) {
            parkingLot = new ParkingLot();
        }
        return parkingLot;
    }

    public Integer getNumberOfFloors() {
        return this.numberOfFloors;
    }

    public Integer addParkingFloor(int size) {

        parkingFloors.add(new ParkingFloor(this.numberOfFloors + 1, size));
        this.numberOfFloors++;

        return this.numberOfFloors;
    }

    public Integer parkVehicle(int floor, Vehicle vehicle) {

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return -1;
        }
        ParkingSpot parkingSpot = parkingFloors.get(floor-1).parkVehicle(vehicle);

        return parkingSpot.getNumber();
    }

    public Integer leave(int floor, Integer slotNumber) {

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return -1;
        }
        ParkingSpot parkingSpot = parkingFloors.get(floor-1).leave(slotNumber);

        return parkingSpot.getNumber();
    }

    public List<String> getAllRegNumbersOfColour(int floor, String colour) {

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return new ArrayList<>();
        }

        return parkingFloors.get(floor-1).getAllRegNumbersOfColour(colour);
    }

    public List<Integer> getAllSlotNumbersOfColor(int floor, String colour) {

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return new ArrayList<>();
        }

        return parkingFloors.get(floor-1).getAllSlotNumbersOfColor(colour);
    }

    public Integer getSlotNumberForVehicle(int floor, String regNumber) {

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return -1;
        }

        return parkingFloors.get(floor-1).getSlotNumberForVehicle(regNumber);
    }

    public List<ParkingSpot> status(int floor) {

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return new ArrayList<>();
        }

        return parkingFloors.get(floor-1).getAllOccupiedParkingSpots();
    }
}
