package parkinglot.Strategy;

import java.util.TreeSet;

public class LeastSlotNumberAvailableStrategy implements ParkingSlotStrategy {

    private TreeSet<Integer> availableParkingSpots;

    public LeastSlotNumberAvailableStrategy() {
        availableParkingSpots = new TreeSet<>();
    }

    @Override
    public void addSlot(Integer slotNumber) {
        availableParkingSpots.add(slotNumber);
    }

    @Override
    public Integer getSlot() {
        if(availableParkingSpots.isEmpty()) {
            return -1;
        }
        return availableParkingSpots.first();
    }

    @Override
    public void useSlot(Integer slotNumber) {
        availableParkingSpots.remove(slotNumber);
    }

    @Override
    public boolean available() {
        return !availableParkingSpots.isEmpty();
    }
}
