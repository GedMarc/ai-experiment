/**
 * Core Events Module
 * 
 * This module provides CloudEvents format and routing for the AI Experiment platform.
 */
module za.co.ai.experiment.libcoreevents {
    // Export the API packages
    exports za.co.ai.experiment.libcoreevents;
    
    // For testing
    opens za.co.ai.experiment.libcoreevents to org.junit.platform.commons;
}