package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import parkinglot.Enums.ParkingSpotType;
import parkinglot.Enums.VehicleType;
import parkinglot.ParkingSpot.ParkingSpot;
import parkinglot.Vehicles.Vehicle;

public class ParkingFloor {

    private int id;

    private int maxSize;

    private HashMap<Integer, ParkingSpot> largeParkingSpots;

    private TreeSet<Integer> availableLargeParkingSpots;

    public ParkingFloor(int id, int maxSize) {
        this.id = id;
        this.maxSize = maxSize;
        largeParkingSpots = new HashMap<>();
        availableLargeParkingSpots = new TreeSet<>();
    }

    public int getId() {
        return this.id;
    }

    public Boolean addParkingSpot(ParkingSpot parkingSpot) {

        Boolean successful = false;
        switch (parkingSpot.getType()) {
            case LARGE:
                largeParkingSpots.put(parkingSpot.getNumber(), parkingSpot);
                availableLargeParkingSpots.add(parkingSpot.getNumber());
                successful = true;
                break ;
            default:
                System.out.println("Invalid parking spot type.");
        }
        return successful;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {

        List<ParkingSpotType> possibleParkingSpotTypes = getParkingSpotTypes(vehicle.getVehicleType());
        ParkingSpot availableParkingSpot = null;
        for (ParkingSpotType parkingSpotType : possibleParkingSpotTypes) {
            availableParkingSpot = getAvailableParkingSpot(parkingSpotType);
            if(availableParkingSpot != null) {
                assignVehicleToParkingSpot(vehicle, availableParkingSpot,
                        getSetOfAvailableParkingSpots(parkingSpotType),
                        getParkingSpots(parkingSpotType));
            }
        }
        return availableParkingSpot;
    }

    public ParkingSpot leave(Integer number, ParkingSpotType parkingSpotType) {

        if(isValidParkingSpotType(parkingSpotType)) {
            return freeParkingSpot(number, getSetOfAvailableParkingSpots(parkingSpotType),
                    getParkingSpots(parkingSpotType));
        }
        System.out.println("Invalid parking spot type provided.");
        return null;
    }

    private TreeSet<Integer> getSetOfAvailableParkingSpots(ParkingSpotType parkingSpotType) {

        if(isValidParkingSpotType(parkingSpotType)) {
            switch (parkingSpotType) {
                case LARGE:
                    return availableLargeParkingSpots;
            }
        }
        return new TreeSet<>();
    }

    private HashMap<Integer, ParkingSpot> getParkingSpots(ParkingSpotType parkingSpotType) {

        if(isValidParkingSpotType(parkingSpotType)) {
            switch (parkingSpotType) {
                case LARGE:
                    return largeParkingSpots;
            }
        }
        return new HashMap<>();
    }

    public List<String> getAllRegNumbersOfColour(String colour) {

        List<ParkingSpot> parkingSpots =  new ArrayList<>(largeParkingSpots.values());

        return parkingSpots
                .stream()
                .filter(parkingSpot -> parkingSpot.getVehicle().getColour().equals(colour))
                .map(parkingSpot -> parkingSpot.getVehicle().getRegNumber())
                .collect(Collectors.toList());
    }

    public Integer getSlotNumberForVehicle(String regNumber) {

        List<ParkingSpot> parkingSpots =  new ArrayList<>(largeParkingSpots.values());

        Optional<ParkingSpot> requiredParkingSpot = parkingSpots.stream()
                .filter(parkingSpot -> parkingSpot.getVehicle().getRegNumber().equals(regNumber))
                .findAny();

        if(!requiredParkingSpot.isPresent()) {
            System.out.println("Vehicle with registration number {" + regNumber + "} not present.");
            return null;
        }
        return requiredParkingSpot.get().getNumber();
    }

    public List<Integer> getAllSlotNumbersOfColor(String colour) {

        List<ParkingSpot> parkingSpots =  new ArrayList<>(largeParkingSpots.values());

        return parkingSpots
                .stream()
                .filter(parkingSpot -> parkingSpot.getVehicle().getColour().equals(colour))
                .map(ParkingSpot::getNumber)
                .collect(Collectors.toList());
    }

    private ParkingSpot freeParkingSpot(Integer number, TreeSet<Integer> availableParkingSpots,
            HashMap<Integer, ParkingSpot> parkingSpots) {

        if(!parkingSpots.containsKey(number)) {
            System.out.println("The parking spot {" + number + "} is already free.");
            return null;
        }
        ParkingSpot parkingSpot = parkingSpots.get(number);
        parkingSpots.remove(number);
        availableParkingSpots.add(number);
        return parkingSpot;
    }

    private void assignVehicleToParkingSpot(Vehicle vehicle, ParkingSpot parkingSpot,
            TreeSet<Integer> availableParkingSpots,
            HashMap<Integer, ParkingSpot> parkingSpots) {

        availableParkingSpots.remove(parkingSpot.getNumber());
        parkingSpots.get(parkingSpot.getNumber()).assignVehicle(vehicle);
    }

    private Boolean isValidVehicleType(VehicleType vehicleType) {

        List<String> vehicleTypes = Stream.of(VehicleType.values())
                .map(VehicleType::name)
                .collect(Collectors.toList());

        return vehicleTypes.contains(vehicleType.name());
    }

    private Boolean isValidParkingSpotType(ParkingSpotType parkingSpotType) {

        List<String> parkingSpotTypes = Stream.of(ParkingSpotType.values())
                .map(ParkingSpotType::name)
                .collect(Collectors.toList());

        return parkingSpotTypes.contains(parkingSpotType.name());
    }

    private List<ParkingSpotType> getParkingSpotTypes(VehicleType vehicleType) {

        List<ParkingSpotType> parkingSpotTypes = new ArrayList<>();
        switch (vehicleType) {
            case CAR:
                parkingSpotTypes.add(ParkingSpotType.LARGE);
                break;
        }
        return parkingSpotTypes;
    }

    private ParkingSpot getAvailableParkingSpot(ParkingSpotType parkingSpotType) {
        ParkingSpot parkingSpot = null;
        switch (parkingSpotType) {
            case LARGE:
                parkingSpot = getAvailableParkingSpot(availableLargeParkingSpots, largeParkingSpots);
                break;
            default:
                System.out.println("Invalid parking spot type.");
        }
        return parkingSpot;
    }

    private ParkingSpot getAvailableParkingSpot(TreeSet<Integer> availableParkingSpots,
            HashMap<Integer, ParkingSpot> parkingSpots) {

        if(availableParkingSpots.isEmpty()) {
            return null;
        }
        Integer firstAvailableParkingSpotNumber = availableParkingSpots.first();
        availableParkingSpots.remove(firstAvailableParkingSpotNumber);

        return parkingSpots.get(firstAvailableParkingSpotNumber);
    }

}
