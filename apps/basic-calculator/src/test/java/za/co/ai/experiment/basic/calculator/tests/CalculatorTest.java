package za.co.ai.experiment.basic.calculator.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import za.co.ai.experiment.basic.calculator.Calculator;

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
}