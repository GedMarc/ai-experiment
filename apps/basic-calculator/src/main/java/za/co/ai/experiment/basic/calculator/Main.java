package za.co.ai.experiment.basic.calculator;

/**
 * Main entry point for the Basic Calculator application.
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Basic Calculator Application Starting...");

        // In a real application, this would initialize the calculator service
        // and start a web server or other interface

        Calculator calculator = new Calculator();

        // Example calculations
        System.out.println("2 + 2 = " + calculator.add(2, 2));
        System.out.println("10 - 5 = " + calculator.subtract(10, 5));
        System.out.println("3 * 4 = " + calculator.multiply(3, 4));
        System.out.println("20 / 4 = " + calculator.divide(20, 4));

        System.out.println("Basic Calculator Application Running!");
    }
}