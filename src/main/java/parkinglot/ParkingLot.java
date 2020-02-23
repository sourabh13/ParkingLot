package parkinglot;

import java.util.ArrayList;
import java.util.List;

import parkinglot.ParkingSpot.ParkingSpot;
import parkinglot.Vehicles.Car;
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

    public void create(int floor, String size) {

        parkingLot.addParkingFloor(Integer.parseInt(size));
        System.out.println("Created a parking lot with " + parkingFloors.get(floor).getMaxSize() + " slots.");
    }

    public Integer getNumberOfFloors() {
        return this.numberOfFloors;
    }

    public Integer addParkingFloor(int size) {

        parkingFloors.add(new ParkingFloor(this.numberOfFloors + 1, size));
        this.numberOfFloors++;

        return this.numberOfFloors;
    }

    public Integer parkVehicle(int floor, String regNumber, String colour) {

        if(parkingLot == null) {
            System.out.println("First create the parking lot.");
            return -1;
        }

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return -1;
        }
        Vehicle vehicle = new Car(regNumber, colour);
        ParkingSpot parkingSpot = parkingFloors.get(floor-1).parkVehicle(vehicle);

        if(parkingSpot == null) {
            System.out.println("Sorry, parking lot is full.");
            return -1;
        }
        System.out.println("Allocated slot number: " + parkingSpot.getNumber());

        return parkingSpot.getNumber();
    }

    public Integer leave(int floor, String slotNumber) {

        if(parkingLot == null) {
            System.out.println("First create the parking lot.");
            return -1;
        }

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return -1;
        }
        ParkingSpot parkingSpot = parkingFloors.get(floor-1).leave(Integer.parseInt(slotNumber));
        System.out.println("Slot number " + parkingSpot.getNumber() + " is free.");

        return parkingSpot.getNumber();
    }

    public List<String> getAllRegNumbersOfColour(int floor, String colour) {

        if(parkingLot == null) {
            System.out.println("First create the parking lot.");
            return new ArrayList<>();
        }

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return new ArrayList<>();
        }
        List<String> regNumbers = parkingFloors.get(floor-1).getAllRegNumbersOfColour(colour);

        for(int i = 0; i < regNumbers.size(); ++i) {
            System.out.print(regNumbers.get(i));
            if(i+1 < regNumbers.size()) {
                System.out.print(", ");
            }
        }
        System.out.print("\n");

        return regNumbers;
    }

    public List<Integer> getAllSlotNumbersOfColor(int floor, String colour) {

        if(parkingLot == null) {
            System.out.println("First create the parking lot.");
            return new ArrayList<>();
        }

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return new ArrayList<>();
        }

        List<Integer> slots = parkingFloors.get(floor-1).getAllSlotNumbersOfColor(colour);
        for(int i = 0; i < slots.size(); ++i) {
            System.out.print(slots.get(i));
            if(i+1 < slots.size()) {
                System.out.print(", ");
            }
        }
        System.out.print("\n");

        return slots;
    }

    public Integer getSlotNumberForVehicle(int floor, String regNumber) {

        if(parkingLot == null) {
            System.out.println("First create the parking lot.");
            return -1;
        }

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return -1;
        }
        Integer slot = parkingFloors.get(floor-1).getSlotNumberForVehicle(regNumber);

        if(slot == null) {
            System.out.println("Not found.");
            return -1;
        }
        System.out.println(slot);

        return slot;
    }

    public List<ParkingSpot> status(int floor) {

        if(parkingLot == null) {
            System.out.println("First create the parking lot.");
            return new ArrayList<>();
        }

        if(floor > numberOfFloors) {
            System.out.println("Invalid floor {" + floor + "} provided.");
            return new ArrayList<>();
        }

        List<ParkingSpot> parkingSpots = parkingFloors.get(floor-1).getAllOccupiedParkingSpots();
        System.out.println("Slot No.\tRegistration No\t\tColour");

        for (ParkingSpot parkingSpot : parkingSpots) {
            System.out.println(parkingSpot.getNumber() + "\t\t" +
                    parkingSpot.getVehicle().getRegNumber() + "\t\t" +
                    parkingSpot.getVehicle().getColour());
        }

        return parkingSpots;
    }
}
