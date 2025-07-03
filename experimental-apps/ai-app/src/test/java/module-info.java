/**
 * AI Application Test Module
 * 
 * This module provides tests for the AI application.
 */
module za.co.ai.experiment.ai.app.tests {
    // Required dependencies
    requires za.co.ai.experiment.ai.app;

    // Vert.x dependencies
    requires io.vertx.core;

    // Test dependencies
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.assertj.core;

    // Export test package to JUnit platform
    exports za.co.ai.experiment.ai.app.tests to org.junit.platform.commons;
    opens za.co.ai.experiment.ai.app.tests to org.junit.platform.commons;
}
