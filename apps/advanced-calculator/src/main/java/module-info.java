/**
 * Advanced Calculator Application Module
 * 
 * This module provides an advanced calculator application for the AI Experiment platform.
 */
module za.co.ai.experiment.advanced.calculator {
    // Export the API packages
    exports za.co.ai.experiment.advanced.calculator;
    
    // Required dependencies
    requires za.co.ai.experiment.libcoreauth;
    requires za.co.ai.experiment.libcoreconfig;
    requires za.co.ai.experiment.libcoreobservability;
    requires za.co.ai.experiment.libcoreevents;
    
    // For testing
    opens za.co.ai.experiment.advanced.calculator to org.junit.platform.commons;
}