package version3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>Fahrenheit <-> Celsius converter</h1>
 * <p>
 * The program converts Celsius to Fahrenheit and wise versa
 * depending on what user selects as an input.
 * It accepts a string of temperatures in one line and converts it
 * into a double array which then is converted into a Map of values
 * in the form {original value(double type) => converted value(Value type}
 * The Value class is a wrapper I created to store the temperature as a double type
 * and keep track of the amount of occurrences of this temperature.
 * The Map is then displayed in the console.
 * </p>
 * <h2>Examples of input and output</h2>
 * <h3>Example 1</h3>
 *
 *       <p><em>Example of input:</em></p>
 *       <p><b>0 10 25 5 37 10 40 55 10</b></p>
 *
 *       <p><em>Example of output:</em></p>
 *       <b><p>Results are:</p>
 *       <ul style="list-style:none;">
 *          <li>0.0&#8451 => 32.0&#8457</li>
 *          <li>10.0&#8451 => 50.0&#8457 (3)</li>
 *          <li>25.0&#8451 => 77.0&#8457</li>
 *          <li>5.0&#8451 => 41.0&#8457</li>
 *          <li>37.0&#8451 => 98.6&#8457</li>
 *          <li>40.0&#8451 => 104.0&#8457</li>
 *          <li>55.0&#8451 => 131.0&#8457</li>
 *       </ul></b>
 * <p>
 *       In the line 2 the output looks like this: 10.0&#8451 => 50.0&#8457 (3)
 *       The number in the parenthesis (3) means the amount of occurrences of this value
 *       (we have '10' celsius repeated three times in the input)
 * </p>
 *
 *     <h3>Example 2</h3>
 *
 *     <p><em>Example of input:</em></p>
 *     <p><b>0, 10 25 ,5 , 37, 0 10</b></p>
 *
 *     <p><em>Example of output:</em></p>
 *     <b><p>Results are:</p>
 *     <ul style="list-style:none;">
 *          <li>0.0&#8451 => 32.0&#8457 (2)</li>
 *          <li>10.0&#8451 => 50.0&#8457 (2)</li>
 *          <li>25.0&#8451 => 77.0&#8457</li>
 *          <li>5.0&#8451 => 41.0&#8457</li>
 *          <li>37.0&#8451 => 98.6&#8457</li>
 *     </ul></b>
 *
 * <p>
 *     Temperature values can be separated with a comma or whitespace or their combination,
 *     as shown in the example above.
 * </p>
 * @author Alexander
 * @version 1.3
 * @since 1.1
 */

public class App3 {
    //lambdas that do the actual converting
    private final DoubleFunction<Double> celsiusFahrenheit = c -> c / 5 * 9 + 32;
    private final DoubleFunction<Double> fahrenheitCelsius = f -> (f - 32) / 9 * 5;
    private DoubleFunction<Double> converter;

    private String from;
    private String to;

    /**
     * Instantiates the class and runs the controller method
     * @param args console arguments
     */
    public static void main(String[] args) {
        //instantiate version3.App3 class and call its controller method
        new App3().controller();
    }

    /**
     *This method checks the input for correctness
     * @param input Temperature scale that user wants to convert from.
     * @return Method returns the first character as a string
     * @throws WrongInputException If wrong scale is selected, then a WrongInputException is thrown
     * @see WrongInputException
     */
    private String checkInput(String input) throws WrongInputException {
        String acceptedInputs = "FC";

        String in = String.valueOf(input.charAt(0)).toUpperCase();

        if (!acceptedInputs.contains(in)) {
            //throws an exception if a user specified wrong temperature scale
            throw new WrongInputException(input);
        }
        return in;
    }

    /**
     * The method sets the scale and converter variables
     * @param scale user selected temperature scale
     */
    private void setFromTo(String scale) {
        if (scale.equals("C")) {
            from = "°C";
            to = "°F";
            converter = celsiusFahrenheit;
        } else {
            from = "°F";
            to = "°C";
            converter = fahrenheitCelsius;
        }
    }

    /**
     * This method accepts a String of values from the user
     * @param values A string of temperature readings. Decimals must be specified with a dot(.)
     * @return An array of double values separated according to the regex
     * <p>
     * Input from the user is converted to double array using Stream API. The String is split using
     * regular expression. It accepts inputs like:
     * '20 30' or '20,30' or '20, 30' or '20 ,30' or '20 , 30'. Input can be mixed up:
     * '10, 20,30 40 ,50 60 , 70' or 10 20, 30 ,40 , 50-60- 70 -80 - 90 |100 | 110/120/ 130 / 140 or
     * 10a 20 a30 a 40a,50 ,a60a,b70* and some others
     * </p>
     */
    private double[] getInput(String values) {
        return Stream.of(values.split("\\W?(\\s|[*\\\\|\\-,/a-zA-Z])\\W?")).mapToDouble(r -> {
            double reading;

            try {
                //parse String to double
                reading = Double.parseDouble(r);
            } catch (NumberFormatException e) {
                //if the value does not qualify, set it to MAX_VALUE, so we can filter it out later
                reading = Double.MAX_VALUE;
            }
            return reading;
            //get rig of the values that do not qualify
        }).filter(r -> r != Double.MAX_VALUE).toArray();
    }

    /**
     * This method controls the execution of the program
     */
    private void controller() {
        //create BufferedReader to get input from the user
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Convert from: Fahrenheit or Celsius [F/C]");
            //get scale from the user
            String scale = checkInput(br.readLine());
            //if checkInput hasn't thrown an exception we can initialize 'from', 'to' variables
            // and 'converter' lambda for the selected scale.
            setFromTo(scale);
            System.out.println("Enter temperature values(in one line). Scale: " + from);
            //get input from the user and convert it to double[]
            double[] readings = getInput(br.readLine());
            //print readings to the console
            //pass 'converter' lambda and 'readings' array to the 'convert' method
            displayReadings(convert(converter, readings));
        } catch (WrongInputException | IOException e) {
            //if an exception has been thrown print it to the console and end the program
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Color.RED + "Error. Input cannot be empty." + Color.RESET);
        }
    }

    /**
     * This method does the Celsius <-> Fahrenheit conversion
     * @param converter lambda that does the conversion
     * @param readings Array of double values representing temperature readings in selected scale
     * @return A LinkedHasMap of values in the form {initial temp(double type) = converted temp(Value type)}
     */
    private Map<Double, Value> convert(DoubleFunction<Double> converter, double[] readings) {
        //convert double array to a Map of readings: {initial temp = converted temp}
        return Arrays.stream(readings).boxed().collect(Collectors.toMap(Function.identity(), t -> {
            //create new Value instance and pass it the converted value
            return new Value(converter.apply(t));
            //if collision occurs, increment occurrences and return the Value object(v1) back
                                    //result is inserted into LinkedHashMap to maintain order
        }, (v1, v2) -> v1.incOcc(), LinkedHashMap::new));
    }

    /**
     * Reads and displays the result in the console
     * @param map A Map of values in the form {initial temp(double type) = converted temp(Value type)}
     */
    private void displayReadings(Map<Double, Value> map) {
        System.out.println("Result:");
        //iterate through the Map and display the results
//        map.forEach((key, value) -> {
//            System.out.printf("%.1f" + from + " => %.1f" + to + (value.getOcc() > 1 ? " (%d)" : "") +
//                    "\n", key, value.getTemp(), value.getOcc());
//        });
        //iterate through the Map and display the results
        int count = 0;
        for (Map.Entry<Double, Value> entry : map.entrySet()) {
            Value value = entry.getValue();
            System.out.print(count % 2 == 0 ? Color.WHITE_BRIGHT : Color.WHITE);
            System.out.printf("%.1f" + from + " => %.1f" + to + (value.getOcc() > 1 ? " (%d)" : "") +
                    "\n", entry.getKey(), value.getTemp(), value.getOcc());
            count++;
        }
    }

    /**
     * nested static class which represents a value in the Map
     */
    private static class Value {
        private double temperature;
        private int occurrences;

        Value(double t) {
            temperature = t;
            occurrences = 1;
        }

        int getOcc() {
            return occurrences;
        }

        double getTemp() {
            return temperature;
        }

        Value incOcc() {
            this.occurrences += 1;
            return this;
        }
    }
}

// Fahrenheit -> Celsius / 5 * 9 + 32
// Celsius -> (Fahrenheit - 32 )/ 9 * 5