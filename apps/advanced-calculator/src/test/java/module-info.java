/**
 * Advanced Calculator Application Test Module
 * 
 * This module provides tests for the advanced calculator application.
 */
module za.co.ai.experiment.advanced.calculator.tests {
    // Required dependencies
    requires za.co.ai.experiment.advanced.calculator;

    // Test dependencies
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.assertj.core;

    // Export test package to JUnit platform
    exports za.co.ai.experiment.advanced.calculator.tests to org.junit.platform.commons;
    opens za.co.ai.experiment.advanced.calculator.tests to org.junit.platform.commons;
}
