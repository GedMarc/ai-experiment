package za.co.ai.experiment.advanced.calculator;

/**
 * Advanced calculator implementation providing extended arithmetic operations.
 */
public class Calculator {
    
    /**
     * Adds two numbers.
     * 
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Subtracts the second number from the first.
     * 
     * @param a First number
     * @param b Second number
     * @return Difference (a - b)
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * Multiplies two numbers.
     * 
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Divides the first number by the second.
     * 
     * @param a First number (dividend)
     * @param b Second number (divisor)
     * @return Quotient (a / b)
     * @throws ArithmeticException if b is zero
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
    
    /**
     * Calculates the square root of a number.
     * 
     * @param a The number
     * @return Square root of a
     * @throws IllegalArgumentException if a is negative
     */
    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(a);
    }
    
    /**
     * Raises a number to a power.
     * 
     * @param base The base
     * @param exponent The exponent
     * @return base raised to the power of exponent
     */
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    /**
     * Calculates the sine of an angle.
     * 
     * @param angleInRadians The angle in radians
     * @return Sine of the angle
     */
    public double sin(double angleInRadians) {
        return Math.sin(angleInRadians);
    }
    
    /**
     * Calculates the cosine of an angle.
     * 
     * @param angleInRadians The angle in radians
     * @return Cosine of the angle
     */
    public double cos(double angleInRadians) {
        return Math.cos(angleInRadians);
    }
    
    /**
     * Calculates the tangent of an angle.
     * 
     * @param angleInRadians The angle in radians
     * @return Tangent of the angle
     */
    public double tan(double angleInRadians) {
        return Math.tan(angleInRadians);
    }
    
    /**
     * Calculates the natural logarithm of a number.
     * 
     * @param a The number
     * @return Natural logarithm of a
     * @throws IllegalArgumentException if a is less than or equal to zero
     */
    public double ln(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Cannot calculate logarithm of non-positive number");
        }
        return Math.log(a);
    }
}