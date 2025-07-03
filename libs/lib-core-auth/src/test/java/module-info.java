/**
 * Core Authentication Test Module
 * 
 * This module provides tests for the OAuth2, JWT, and JWKS handling functionality.
 */
module za.co.ai.experiment.libcoreauth.tests {
    // Required dependencies
    requires za.co.ai.experiment.libcoreauth;

    // Test dependencies
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.assertj.core;

    // Export test package to JUnit platform
    exports za.co.ai.experiment.libcoreauth.tests to org.junit.platform.commons;
    opens za.co.ai.experiment.libcoreauth.tests to org.junit.platform.commons;
}
