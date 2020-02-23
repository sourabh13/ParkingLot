package parkinglot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import parkinglot.Enums.ParkingSpotType;
import parkinglot.Enums.VehicleType;
import parkinglot.ParkingSpot.LargeParkingSpot;
import parkinglot.ParkingSpot.ParkingSpot;
import parkinglot.Strategy.LeastSlotNumberAvailableStrategy;
import parkinglot.Strategy.ParkingSlotStrategy;
import parkinglot.Vehicles.Vehicle;

public class ParkingFloor {

    private int id;

    private int maxSize;

    private HashMap<Integer, ParkingSpot> largeParkingSpots;

    private ParkingSlotStrategy largeParkingSlotStrategy;

    public ParkingFloor(int id, int maxSize) {
        this.id = id;
        this.maxSize = maxSize;
        largeParkingSpots = new HashMap<>();
        largeParkingSlotStrategy = new LeastSlotNumberAvailableStrategy();
        for(int i = 1; i <= maxSize; ++i) {
            largeParkingSlotStrategy.addSlot(i);
            largeParkingSpots.put(i, new LargeParkingSpot(i));
        }

    }

    public int getId() {
        return this.id;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public Boolean addParkingSpot(ParkingSpot parkingSpot) {

        Boolean successful = false;
        switch (parkingSpot.getType()) {
            case LARGE:
                largeParkingSpots.put(parkingSpot.getNumber(), parkingSpot);
                largeParkingSlotStrategy.addSlot(parkingSpot.getNumber());
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
                        getParkingSlotStrategy(parkingSpotType),
                        getParkingSpots(parkingSpotType));
            }
        }
        return availableParkingSpot;
    }

    public ParkingSpot leave(Integer number) {
        return leave(number, ParkingSpotType.LARGE);
    }

    public ParkingSpot leave(Integer number, ParkingSpotType parkingSpotType) {

        if(isValidParkingSpotType(parkingSpotType)) {
            return freeParkingSpot(number, getParkingSlotStrategy(parkingSpotType),
                    getParkingSpots(parkingSpotType));
        }
        System.out.println("Invalid parking spot type provided.");
        return null;
    }

    public List<ParkingSpot> getAllOccupiedParkingSpots() {

        List<ParkingSpot> parkingSpots = getOccupiedParkingSpotsForAllTypes();

        return parkingSpots.stream()
                .sorted(Comparator.comparingInt(ParkingSpot::getNumber))
                .collect(Collectors.toList());
    }

    private ParkingSlotStrategy getParkingSlotStrategy(ParkingSpotType parkingSpotType) {

        if(isValidParkingSpotType(parkingSpotType)) {
            switch (parkingSpotType) {
                case LARGE:
                    return largeParkingSlotStrategy;
            }
        }

        return null;
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

        List<ParkingSpot> parkingSpots = getOccupiedParkingSpotsForAllTypes();

        return parkingSpots
                .stream()
                .filter(parkingSpot -> parkingSpot.getVehicle().getColour().equals(colour))
                .map(parkingSpot -> parkingSpot.getVehicle().getRegNumber())
                .collect(Collectors.toList());
    }

    public Integer getSlotNumberForVehicle(String regNumber) {

        List<ParkingSpot> parkingSpots =  getOccupiedParkingSpotsForAllTypes();

        Optional<ParkingSpot> requiredParkingSpot = parkingSpots.stream()
                .filter(parkingSpot -> parkingSpot.getVehicle().getRegNumber().equals(regNumber))
                .findAny();

        if(!requiredParkingSpot.isPresent()) {
            return null;
        }
        return requiredParkingSpot.get().getNumber();
    }

    public List<Integer> getAllSlotNumbersOfColor(String colour) {

        List<ParkingSpot> parkingSpots = getOccupiedParkingSpotsForAllTypes();

        return parkingSpots
                .stream()
                .filter(parkingSpot -> parkingSpot.getVehicle().getColour().equals(colour))
                .map(ParkingSpot::getNumber)
                .collect(Collectors.toList());
    }

    private List<ParkingSpot> getOccupiedParkingSpotsForAllTypes() {

        List<ParkingSpot> result = new ArrayList<>();
        for (ParkingSpotType parkingSpotType : ParkingSpotType.values()) {
            HashMap<Integer, ParkingSpot> parkingSpotsMap = getParkingSpots(parkingSpotType);
            List<ParkingSpot> parkingSpots = new ArrayList<>(parkingSpotsMap.values());
            result.addAll(parkingSpots.stream()
                    .filter(parkingSpot -> parkingSpot.getVehicle() != null)
                    .collect(Collectors.toList()));
        }
        return result;
    }

    private ParkingSpot freeParkingSpot(Integer number, ParkingSlotStrategy parkingSlotStrategy,
            HashMap<Integer, ParkingSpot> parkingSpots) {

        if(!parkingSpots.containsKey(number)) {
            System.out.println("The parking spot {" + number + "} is already free.");
            return null;
        }
        ParkingSpot parkingSpot = parkingSpots.get(number);
        parkingSpot.removeVehicle();
        parkingSlotStrategy.addSlot(number);
        return parkingSpot;
    }

    private void assignVehicleToParkingSpot(Vehicle vehicle, ParkingSpot parkingSpot,
            ParkingSlotStrategy parkingSlotStrategy,
            HashMap<Integer, ParkingSpot> parkingSpots) {

        parkingSlotStrategy.useSlot(parkingSpot.getNumber());
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
                parkingSpot = getAvailableParkingSpot(largeParkingSlotStrategy, largeParkingSpots);
                break;
            default:
                System.out.println("Invalid parking spot type.");
        }
        return parkingSpot;
    }

    private ParkingSpot getAvailableParkingSpot(ParkingSlotStrategy parkingSlotStrategy,
            HashMap<Integer, ParkingSpot> parkingSpots) {

        if(!parkingSlotStrategy.available()) {
            return null;
        }
        Integer parkingSpotNumber = parkingSlotStrategy.getSlot();
        parkingSlotStrategy.useSlot(parkingSpotNumber);

        return parkingSpots.get(parkingSpotNumber);
    }

}
