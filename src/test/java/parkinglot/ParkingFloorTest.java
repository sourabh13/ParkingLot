package parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import parkinglot.Enums.ParkingSpotType;
import parkinglot.ParkingSpot.LargeParkingSpot;
import parkinglot.ParkingSpot.ParkingSpot;
import parkinglot.Vehicles.Car;
import parkinglot.Vehicles.Vehicle;

public class ParkingFloorTest {

    @Test
    public void addParkingSpotTest() {

        ParkingFloor parkingFloor = new ParkingFloor(1,1);
        ParkingSpot parkingSpot = new LargeParkingSpot(1);
        Boolean successful = parkingFloor.addParkingSpot(parkingSpot);
        Boolean expected = Boolean.TRUE;
        assertEquals(expected, successful);
    }

    @Test
    public void parkVehicleTest() {

        ParkingFloor parkingFloor = new ParkingFloor(1, 1);
        ParkingSpot parkingSpot = new LargeParkingSpot(1);
        Vehicle vehicle = new Car("ABCD-EFGH", "White");
        parkingFloor.addParkingSpot(parkingSpot);
        ParkingSpot actualParkingSpot = parkingFloor.parkVehicle(vehicle);
        assertEquals(parkingSpot.getNumber(), actualParkingSpot.getNumber());
        assertEquals(ParkingSpotType.LARGE, parkingSpot.getType());
    }

    @Test
    public void leaveParkingTest() {

        ParkingFloor parkingFloor = new ParkingFloor(1, 1);
        ParkingSpot parkingSpot = new LargeParkingSpot(1);
        Vehicle vehicle = new Car("ABCD-EFGH", "White");
        parkingFloor.addParkingSpot(parkingSpot);
        parkingFloor.parkVehicle(vehicle);
        ParkingSpot actualParkingSpot = parkingFloor.leave(1, ParkingSpotType.LARGE);
        assertEquals(parkingSpot.getNumber(), actualParkingSpot.getNumber());
        assertEquals(ParkingSpotType.LARGE, parkingSpot.getType());
    }

    @Test
    public void getAllRegNumbersOfColourTest() {

        ParkingFloor parkingFloor = createSampleParkingFloor(4);

        List<String> allRegsOfColor = parkingFloor.getAllRegNumbersOfColour("White");

        assertEquals(3, allRegsOfColor.size());
        assertTrue(allRegsOfColor.contains("ABCD-EFGH"));
        assertTrue(allRegsOfColor.contains("IJKL-MNOP"));
        assertTrue(allRegsOfColor.contains("IJKL-EFGH"));
        assertTrue(!allRegsOfColor.contains("ABCD-MNOP"));
    }

    @Test
    public void getAllSlotsOfColorTest() {

        ParkingFloor parkingFloor = createSampleParkingFloor(4);

        List<Integer> allSlotNumbersOfColor = parkingFloor.getAllSlotNumbersOfColor("White");

        assertEquals(3, allSlotNumbersOfColor.size());
        assertTrue(allSlotNumbersOfColor.contains(1));
        assertTrue(allSlotNumbersOfColor.contains(2));
        assertTrue(allSlotNumbersOfColor.contains(4));
        assertTrue(!allSlotNumbersOfColor.contains(3));

    }

    @Test
    public void getSlotNumberForVehiclePresentTest() {

        ParkingFloor parkingFloor = createSampleParkingFloor(4);
        Integer slotNumber = parkingFloor.getSlotNumberForVehicle("ABCD-MNOP");

        assertNotNull(slotNumber);
        assertEquals(3, slotNumber.intValue());
    }

    @Test
    public void getSlotNumberForVehicleAbsentTest() {

        ParkingFloor parkingFloor = createSampleParkingFloor(4);
        Integer slotNumber = parkingFloor.getSlotNumberForVehicle("XXXX-XXXX");

        assertNull(slotNumber);
    }

    @Test
    public void getAllOccupiedParkingSpotsTest() {

        ParkingFloor parkingFloor = createSampleParkingFloor(8);
        List<ParkingSpot> occupiedParkingSpots = parkingFloor.getAllOccupiedParkingSpots();

        assertNotNull(occupiedParkingSpots);
        assertEquals(4, occupiedParkingSpots.size());
        assertEquals("ABCD-EFGH", occupiedParkingSpots.get(0).getVehicle().getRegNumber());
        assertEquals("IJKL-MNOP", occupiedParkingSpots.get(1).getVehicle().getRegNumber());
        assertEquals("ABCD-MNOP", occupiedParkingSpots.get(2).getVehicle().getRegNumber());
        assertEquals("IJKL-EFGH", occupiedParkingSpots.get(3).getVehicle().getRegNumber());
    }


    private ParkingFloor createSampleParkingFloor(int size) {

        ParkingFloor parkingFloor = new ParkingFloor(1, size);
        parkingFloor.addParkingSpot(new LargeParkingSpot(1));
        parkingFloor.addParkingSpot(new LargeParkingSpot(2));
        parkingFloor.addParkingSpot(new LargeParkingSpot(3));
        parkingFloor.addParkingSpot(new LargeParkingSpot(4));

        parkingFloor.parkVehicle(new Car("ABCD-EFGH", "White"));
        parkingFloor.parkVehicle(new Car("IJKL-MNOP", "White"));
        parkingFloor.parkVehicle(new Car("ABCD-MNOP", "Red"));
        parkingFloor.parkVehicle(new Car("IJKL-EFGH", "White"));

        return parkingFloor;
    }
}
