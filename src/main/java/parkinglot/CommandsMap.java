package parkinglot;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import parkinglot.Vehicles.Vehicle;

public class CommandsMap {

    public Map<String, Method> commands;

    public CommandsMap() {
        commands = new HashMap<>();
        try {
            createCommandsMap();
        } catch (NoSuchMethodException e) {
            System.out.println("Error creating the commands mapping.");
            e.printStackTrace();
        }
    }

    private void createCommandsMap() throws NoSuchMethodException {
        commands.put("create_parking_lot", ParkingLot.class.getMethod("create", int.class, String.class));
        commands.put("park", ParkingLot.class.getMethod("parkVehicle", int.class, String.class, String.class));
        commands.put("leave", ParkingLot.class.getMethod("leave", int.class, String.class));
        commands.put("status", ParkingLot.class.getMethod("status", int.class));
        commands.put("registration_numbers_for_cars_with_colour",
                ParkingLot.class.getMethod("getAllRegNumbersOfColour", int.class, String.class));
        commands.put("slot_numbers_for_cars_with_colour",
                ParkingLot.class.getMethod("getAllSlotNumbersOfColor", int.class, String.class));
        commands.put("slot_number_for_registration_number",
                ParkingLot.class.getMethod("getSlotNumberForVehicle", int.class, String.class));
    }
}
