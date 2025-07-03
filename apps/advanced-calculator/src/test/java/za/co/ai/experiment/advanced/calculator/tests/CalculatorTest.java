package za.co.ai.experiment.advanced.calculator.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import za.co.ai.experiment.advanced.calculator.Calculator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Calculator class.
 */
public class CalculatorTest {
    
    private final Calculator calculator = new Calculator();
    
    @Test
    @DisplayName("Addition: 2 + 2 = 4")
    void testAddition() {
        assertEquals(4, calculator.add(2, 2), "2 + 2 should equal 4");
    }
    
    @Test
    @DisplayName("Subtraction: 10 - 5 = 5")
    void testSubtraction() {
        assertEquals(5, calculator.subtract(10, 5), "10 - 5 should equal 5");
    }
    
    @Test
    @DisplayName("Multiplication: 3 * 4 = 12")
    void testMultiplication() {
        assertEquals(12, calculator.multiply(3, 4), "3 * 4 should equal 12");
    }
    
    @Test
    @DisplayName("Division: 20 / 4 = 5")
    void testDivision() {
        assertEquals(5, calculator.divide(20, 4), "20 / 4 should equal 5");
    }
    
    @Test
    @DisplayName("Division by zero should throw ArithmeticException")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0),
                "Division by zero should throw ArithmeticException");
    }
    
    @Test
    @DisplayName("Square root: sqrt(16) = 4")
    void testSquareRoot() {
        assertEquals(4.0, calculator.sqrt(16), 0.0001, "Square root of 16 should be 4");
    }
    
    @Test
    @DisplayName("Square root of negative number should throw IllegalArgumentException")
    void testSquareRootOfNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> calculator.sqrt(-1),
                "Square root of negative number should throw IllegalArgumentException");
    }
    
    @Test
    @DisplayName("Power: 2^3 = 8")
    void testPower() {
        assertEquals(8.0, calculator.power(2, 3), 0.0001, "2^3 should be 8");
    }
    
    @Test
    @DisplayName("Sine: sin(0) = 0")
    void testSin() {
        assertEquals(0.0, calculator.sin(0), 0.0001, "sin(0) should be 0");
    }
    
    @Test
    @DisplayName("Cosine: cos(0) = 1")
    void testCos() {
        assertEquals(1.0, calculator.cos(0), 0.0001, "cos(0) should be 1");
    }
    
    @Test
    @DisplayName("Tangent: tan(0) = 0")
    void testTan() {
        assertEquals(0.0, calculator.tan(0), 0.0001, "tan(0) should be 0");
    }
    
    @Test
    @DisplayName("Natural logarithm: ln(1) = 0")
    void testLn() {
        assertEquals(0.0, calculator.ln(1), 0.0001, "ln(1) should be 0");
    }
    
    @Test
    @DisplayName("Natural logarithm of non-positive number should throw IllegalArgumentException")
    void testLnOfNonPositiveNumber() {
        assertThrows(IllegalArgumentException.class, () -> calculator.ln(0),
                "Natural logarithm of non-positive number should throw IllegalArgumentException");
    }
}