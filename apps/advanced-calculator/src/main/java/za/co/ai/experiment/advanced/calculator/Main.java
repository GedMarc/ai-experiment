package za.co.ai.experiment.advanced.calculator;

import java.util.Map;
import za.co.ai.experiment.libcoreevents.CloudEvent;

/**
 * Main entry point for the Advanced Calculator application.
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Advanced Calculator Application Starting...");

        // In a real application, this would initialize the calculator service
        // and start a web server or other interface

        Calculator calculator = new Calculator();

        // Example calculations
        System.out.println("2 + 2 = " + calculator.add(2, 2));
        System.out.println("10 - 5 = " + calculator.subtract(10, 5));
        System.out.println("3 * 4 = " + calculator.multiply(3, 4));
        System.out.println("20 / 4 = " + calculator.divide(20, 4));

        // Advanced features
        System.out.println("Square root of 16 = " + calculator.sqrt(16));
        System.out.println("2^3 = " + calculator.power(2, 3));

        // Example of using CloudEvent for calculation result
        CloudEvent event = CloudEvent.builder()
                .source("advanced-calculator")
                .type("calculation.completed")
                .subject("addition")
                .data(Map.of("operation", "add", "result", calculator.add(5, 7)))
                .build();

        System.out.println("Created event: " + event.getId());

        System.out.println("Advanced Calculator Application Running!");
    }
}
