package parkinglot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputParser {

    private CommandsMap commandsMap;

    private ParkingLot parkingLot;

    public InputParser() {
        commandsMap = new CommandsMap();
        parkingLot = ParkingLot.getInstance();
    }

    public void parseInput(String input) {
        String[] inputs = input.split(" ");
        int floor = parkingLot.getNumberOfFloors();
        switch (inputs.length) {
            case 1:
                try {
                    Method method = commandsMap.commands.get(input);
                    if (method != null) {
                        method.invoke(parkingLot, floor);
                    } else {
                        System.out.println("Invalid input");
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    Method method = commandsMap.commands.get(inputs[0]);
                    if (method != null) {
                        method.invoke(parkingLot, floor, inputs[1]);
                    } else {
                        System.out.println("Invalid input");
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    Method method = commandsMap.commands.get(inputs[0]);
                    if (method != null) {
                        method.invoke(parkingLot, floor, inputs[1], inputs[2]);
                    } else {
                        System.out.println("Invalid input");
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid input.");
        }
    }

    public void parseFileInput(String filePath) {
        File inputFile = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    parseInput(line.trim());
                }
            } catch (IOException e) {
                System.out.println("Error reading the input file.");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error : File not found in the path specified.");
            e.printStackTrace();
        }
    }

}
