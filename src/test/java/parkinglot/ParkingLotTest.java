package parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import parkinglot.Vehicles.Car;

public class ParkingLotTest {

    private static ParkingLot parkingLot;

    @BeforeClass
    public static void setupParkingLot() {
        parkingLot = ParkingLot.getInstance();
        parkingLot.addParkingFloor(4);
    }

    @Test
    public void parkVehicleBad() {
        Integer result = parkingLot.parkVehicle(2, new Car("ABCD-EFGH", "White"));

        assertEquals(-1, result.intValue());
    }

    @Test
    public void leaveBad() {
        Integer result = parkingLot.leave(2, 2);

        assertEquals(-1, result.intValue());
    }

    @Test
    public void getAllRegNumbersOfColourBad() {

        assertTrue(parkingLot.getAllRegNumbersOfColour(2, "White").isEmpty());
    }

    @Test
    public void getAllSlotNumbersOfColorBad() {

        assertTrue(parkingLot.getAllSlotNumbersOfColor(2, "White").isEmpty());
    }

    @Test
    public void getSlotNumberForVehicleBad() {

        assertEquals(-1, parkingLot.getSlotNumberForVehicle(2, "White").intValue());
    }

}
