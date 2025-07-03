/**
 * Core Configuration Module
 * 
 * This module provides shared environment and configuration parsing utilities for the AI Experiment platform.
 */
module za.co.ai.experiment.libcoreconfig {
    // Export the API packages
    exports za.co.ai.experiment.libcoreconfig;
    
    // For testing
    opens za.co.ai.experiment.libcoreconfig to org.junit.platform.commons;
}