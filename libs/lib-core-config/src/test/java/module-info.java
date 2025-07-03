/**
 * Core Configuration Test Module
 * 
 * This module provides tests for the shared environment and configuration parsing utilities.
 */
module za.co.ai.experiment.libcoreconfig.tests {
    // Required dependencies
    requires za.co.ai.experiment.libcoreconfig;

    // Test dependencies
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.assertj.core;

    // Export test package to JUnit platform
    exports za.co.ai.experiment.libcoreconfig.tests to org.junit.platform.commons;
    opens za.co.ai.experiment.libcoreconfig.tests to org.junit.platform.commons;
}
