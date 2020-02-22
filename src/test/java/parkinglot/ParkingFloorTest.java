package parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import parkinglot.Enums.ParkingSpotType;
import parkinglot.ParkingSpot.LargeParkingSpot;
import parkinglot.ParkingSpot.ParkingSpot;
import parkinglot.Vehicles.Car;
import parkinglot.Vehicles.Vehicle;

public class ParkingFloorTest {



    @Test
    public void addParkingSpotTest() {

        ParkingFloor parkingFloor = new ParkingFloor(1);
        ParkingSpot parkingSpot = new LargeParkingSpot(1);
        Boolean successful = parkingFloor.addParkingSpot(parkingSpot);
        Boolean expected = Boolean.TRUE;
        assertEquals(expected, successful);
    }

    @Test
    public void parkVehicleTest() {

        ParkingFloor parkingFloor = new ParkingFloor(1);
        ParkingSpot parkingSpot = new LargeParkingSpot(1);
        Vehicle vehicle = new Car("ABCD-EFGH", "White");
        parkingFloor.addParkingSpot(parkingSpot);
        ParkingSpot actualParkingSpot = parkingFloor.parkVehicle(vehicle);
        assertEquals(parkingSpot.getNumber(), actualParkingSpot.getNumber());
        assertEquals(ParkingSpotType.LARGE, parkingSpot.getType());
    }

    @Test
    public void leaveParkingTest() {

        ParkingFloor parkingFloor = new ParkingFloor(1);
        ParkingSpot parkingSpot = new LargeParkingSpot(1);
        Vehicle vehicle = new Car("ABCD-EFGH", "White");
        parkingFloor.addParkingSpot(parkingSpot);
        parkingFloor.parkVehicle(vehicle);
        ParkingSpot actualParkingSpot = parkingFloor.leave(1, ParkingSpotType.LARGE);
        assertEquals(parkingSpot.getNumber(), actualParkingSpot.getNumber());
        assertEquals(ParkingSpotType.LARGE, parkingSpot.getType());
    }

}
