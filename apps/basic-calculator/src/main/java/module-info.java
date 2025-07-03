/**
 * Basic Calculator Application Module
 * 
 * This module provides a basic calculator application for the AI Experiment platform.
 */
module za.co.ai.experiment.basic.calculator {
    // Export the API packages
    exports za.co.ai.experiment.basic.calculator;

    // Required dependencies
    requires za.co.ai.experiment.libcoreauth;
    requires za.co.ai.experiment.libcoreconfig;
    requires za.co.ai.experiment.libcoreobservability;

    // For testing
    opens za.co.ai.experiment.basic.calculator to org.junit.platform.commons;
}
