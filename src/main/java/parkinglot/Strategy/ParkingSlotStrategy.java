package parkinglot.Strategy;

public interface ParkingSlotStrategy {

    void addSlot(Integer slotNumber);

    Integer getSlot();

    void useSlot(Integer slotNumber);

    boolean available();
}
