/**
 * Core Events Test Module
 * 
 * This module provides tests for the CloudEvents format and routing functionality.
 */
module za.co.ai.experiment.libcoreevents.tests {
    // Required dependencies
    requires za.co.ai.experiment.libcoreevents;

    // Test dependencies
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.assertj.core;

    // Export test package to JUnit platform
    exports za.co.ai.experiment.libcoreevents.tests to org.junit.platform.commons;
    opens za.co.ai.experiment.libcoreevents.tests to org.junit.platform.commons;
}
