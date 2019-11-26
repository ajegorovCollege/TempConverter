package version2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App2 {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new App2().controller();
    }

    private void controller() {
        System.out.println("Convert from: Fahrenheit or Celsius [F/C]");
        //get the scale to convert from
        String scale = sc.nextLine().toUpperCase();
        //check if input is correct otherwise throw an exception
        if (scale.charAt(0) != 'F' && scale.charAt(0) != 'C') {
            throw new InputMismatchException(scale);
        }
        //create a double[] with the length of
        double[] readings = new double[10];
        int count = 0;
        //reading can be entered in a line or separately(no commas; decimals separated with a dot)
        System.out.println("Reading in " + (scale.equals("C") ? "℃" : "℉"));
        //iterate through the array
        while (count < readings.length) {
            try {
                //ask for temperature reading and store it at the 'count' index in the array
                //if it throws an error here, the 'count' variable will not be incremented
                double input = sc.nextDouble();
                readings[count++] = input;
            } catch (InputMismatchException e) {
                //display message to the user and try getting input again
                System.out.println("Can't recognize input. ");
                //iterate while input has wrong characters; consume bad characters
                while (!sc.hasNextDouble()) sc.next();
            }
        }
        System.out.println("Results:");
        //call the appropriate method
        if (scale.equals("C")) ctof(readings);
        else ftoc(readings);
    }

    //display celsius to fahrenheit conversion
    private void ctof(double[] readings) {
        for (double temp : readings) {
            System.out.printf("%.1f℃ is %.1f℉ \n", temp, (temp / 5 * 9 + 32));
        }
    }

    //display fahrenheit to celsius conversion
    private void ftoc(double[] readings) {
        for (double temp : readings) {
            System.out.printf("%.1f℉ is %.1f℃ \n", temp, ((temp - 32) / 9 * 5));
        }
    }
}

// Fahrenheit -> Celsius / 5 * 9 + 32
// Celsius -> (Fahrenheit - 32 )/ 9 * 5