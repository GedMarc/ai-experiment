/**
 * Core Authentication Module
 * 
 * This module provides OAuth2, JWT, and JWKS handling for the AI Experiment platform.
 */
module za.co.ai.experiment.libcoreauth {
    // Export the API packages
    exports za.co.ai.experiment.libcoreauth;

    // For testing
    opens za.co.ai.experiment.libcoreauth to org.junit.platform.commons;
}
