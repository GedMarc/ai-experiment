/**
 * Core Observability Test Module
 * 
 * This module provides tests for the GCP-native log/trace integrations functionality.
 */
module za.co.ai.experiment.libcoreobservability.tests {
    // Required dependencies
    requires za.co.ai.experiment.libcoreobservability;

    // Test dependencies
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.assertj.core;

    // Export test package to JUnit platform
    exports za.co.ai.experiment.libcoreobservability.tests to org.junit.platform.commons;
    opens za.co.ai.experiment.libcoreobservability.tests to org.junit.platform.commons;
}
