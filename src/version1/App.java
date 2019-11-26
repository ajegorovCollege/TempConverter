package version1;

import java.util.*;
import java.util.stream.Collectors;

public class App {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new App().controller();
    }

    private void controller() {
        System.out.println("Convert from Fahrenheit or Celsius [F/C]");
        String scale = sc.nextLine().toUpperCase();

        List<Double> readings = new ArrayList<>();

        while (true) {
            System.out.println("Enter new reading? [y/n]");
            if (sc.nextLine().toUpperCase().equals("Y")) {

                try {
                    System.out.println("Reading: ");
                    readings.add(sc.nextDouble());
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input. Try again.");
                }
                sc.nextLine();

            } else {
                break;
            }
        }
        System.out.println(readings);
        if (scale.equals("C")) {
            ctof(readings);
        } else if (scale.equals("F")) {
            ftof(readings);
        }
    }

    private void ctof(List<Double> readings) {

        Map<Double, Double> celsiusFahrenheit = readings.stream().collect(Collectors.toMap(c -> c, c -> c / 5 * 9 + 32));
        System.out.println(celsiusFahrenheit);

        for (Map.Entry<Double, Double> reading: celsiusFahrenheit.entrySet()) {
            System.out.println(reading.getKey() + " celsius is: " + reading.getValue() + " fahrenheit");
        }
//        return celsiusFahrenheit;

//        readings.forEach(temp -> {
//            System.out.print(temp + " celsius is: ");
//            System.out.println((temp / 5 * 9 + 32) + " fahrenheit");
//        });


//        for (Double temp: readings) {
//            System.out.print(temp + " celsius is: ");
//            System.out.println((temp / 5 * 9 + 32) + " fahrenheit");
//        }

//        System.out.println(Arrays.toString(readings.stream().mapToDouble(t -> t / 5 * 9 + 32).toArray()));
    }

    private void ftof(List<Double> readings) {
        Map<Double, Double> fahrenheitCelsius = readings.stream().collect(Collectors.toMap(f -> f, f -> (f - 32) / 9 * 5));
        System.out.println(fahrenheitCelsius);

        for (Map.Entry<Double, Double> reading: fahrenheitCelsius.entrySet()) {
            System.out.println(reading.getKey() + " fahrenheit is: " + reading.getValue() + " celsius");
        }
    }
}

// Fahrenheit -> Celcius / 5 * 9 + 32
// Celcius -> (Fahrenheit - 32 )/ 9 * 5