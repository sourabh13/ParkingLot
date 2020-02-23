package parkinglot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

    public static void main(String[] args) {
        InputParser parser = new InputParser();

        switch (args.length) {
            case 0:
                while(true) {
                    try {
                        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                        String inputString = bufferRead.readLine();
                        if (inputString.equalsIgnoreCase("exit")) {
                            break;
                        } else if (!inputString.isEmpty()) {
                            parser.parseInput(inputString.trim());
                        }
                    } catch (IOException e) {
                        System.out.println("Error in reading the input from console.");
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                parser.parseFileInput(args[0]);
                break;
            default:
                System.out.println("Invalid input provided.");
        }
    }
}
